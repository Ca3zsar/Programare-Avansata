package com.cezartodirisca;

import Classes.Director;
import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.DirectorDAO;
import DAOClasses.MovieDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
}
