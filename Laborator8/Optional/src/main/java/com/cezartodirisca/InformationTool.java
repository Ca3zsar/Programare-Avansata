package com.cezartodirisca;

import Classes.Director;
import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.DirectorDAO;
import DAOClasses.MovieDAO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class InformationTool {
    private Reader CSVIn;

    public InformationTool(String fileName)
    {
        try {
            this.CSVIn = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddInformation(int moviesID, int directorsID) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.getInstance().getConnection();
        try(CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(CSVIn)) {
            DAO<Movie> movieAdder = new MovieDAO(connection);
            DAO<Director> directorAdder = new DirectorDAO(connection);
            Pattern pattern = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");
            for(CSVRecord record : csvParser)
            {
                String title = record.get("title");
                String date = record.get("date_published");
                if(!pattern.matcher(date).matches())
                {
                    continue;
                }

                date = date.replace("/","-");

                String duration = record.get("duration");
                String score = record.get("avg_vote");
                String director = record.get("director");

                movieAdder.insert(moviesID, new Movie(title,date,Integer.parseInt(duration),Double.parseDouble(score)));
                directorAdder.insert(directorsID, new Director(director,"01-01-1900"));

                PreparedStatement prepared = connection.prepareStatement("INSERT INTO directors_movies VALUES(?,?)");
                prepared.setInt(1,moviesID);
                prepared.setInt(2,directorsID);

                prepared.executeUpdate();
                prepared.close();

                moviesID++;
                directorsID++;
            }
        } catch (IOException | SQLException exception) {
            exception.printStackTrace();
        }
        connection.close();
    }

    public List<List<String>> directedMovies() throws SQLException, ClassNotFoundException {
        Connection connection = JDBCConnection.getInstance().getConnection();
        List<List<String>> toReturn = new ArrayList<>();
        try {
            PreparedStatement prepared = connection.prepareStatement("SELECT d.name, m.title FROM directors d JOIN" +
                    " directors_movies dm ON d.id = dm.director_id JOIN movies m ON m.id=dm.movie_id");
            ResultSet results = prepared.executeQuery();

            while(results.next())
            {
                String directorName = results.getString(1);
                String title = results.getString(2);

                toReturn.add(Arrays.asList(directorName,title));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connection.close();
        return toReturn;
    }

    public void report() throws IOException, TemplateException, SQLException, ClassNotFoundException {
        Configuration configuration = new Configuration();

        configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator8\\Optional\\src\\resources"));

        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<>();

        input.put("title", "Data Information");

        List<String> entriesDescription = new ArrayList<>();
        // Get the info from the database
        Connection connection = JDBCConnection.getInstance().getConnection();

        PreparedStatement prepared = connection.prepareStatement("SELECT count(*) FROM movies");
        ResultSet result = prepared.executeQuery();
        result.next();
        entriesDescription.add("There are " + result.getString(1) + " movies in the database");

        prepared = connection.prepareStatement("SELECT count(distinct name) FROM directors");
        result = prepared.executeQuery();
        result.next();
        entriesDescription.add("There are " + result.getString(1) + " directors in the database");

        input.put("entries", entriesDescription);

        prepared = connection.prepareStatement("SELECT * FROM (SELECT d.name,count(*) maxim FROM " +
                "directors d JOIN directors_movies dm ON d.id = dm.director_id JOIN movies m ON " +
                "m.id=dm.movie_id group by d.name order by maxim desc) where rownum=1");
        result = prepared.executeQuery();
        result.next();

        entriesDescription.add(result.getString(1) + " directed the most movies ( " + result.getString(2) + ")");

        Template template = configuration.getTemplate("report.ftl");

        try (Writer fileWriter = new FileWriter("output.html")) {
            template.process(input, fileWriter);

            File entryFile = new File("output.html");
            Desktop desktop = Desktop.getDesktop();

            desktop.open(entryFile);
        }
    }
}
