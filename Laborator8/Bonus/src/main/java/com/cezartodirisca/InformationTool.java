package com.cezartodirisca;

import Classes.Director;
import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.DirectorDAO;
import DAOClasses.MovieDAO;
import com.github.javafaker.Faker;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class InformationTool{

    public InformationTool()
    {
    }

    public void AddInformation(int moviesID, int directorsID,int max,int offset,Connection connection) throws SQLException {
        DAO<Movie> movieAdder = new MovieDAO(connection);
        DAO<Director> directorAdder = new DirectorDAO(connection);
        Faker faker = new Faker();
        for (int i = 0 ;i<10000;i++) {
            String title = "not such a nice title";
            String date = "10-12-2000";

            String duration = "10";
            String score = "9.1";
            String director = faker.name().name();

            movieAdder.insert(moviesID, new Movie(title, date, Integer.parseInt(duration), Double.parseDouble(score)));
            directorAdder.insert(directorsID, new Director(director, "01-01-1900"));

            PreparedStatement prepared;
            try {
                prepared = connection.prepareStatement("INSERT INTO directors_movies VALUES(?,?)");
                prepared.setInt(1, moviesID);
                prepared.setInt(2, directorsID);

                prepared.executeUpdate();
                prepared.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            moviesID++;
            directorsID++;
        }
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
