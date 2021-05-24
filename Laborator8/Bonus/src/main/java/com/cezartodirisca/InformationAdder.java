package com.cezartodirisca;

import Classes.Director;
import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.DirectorDAO;
import DAOClasses.MovieDAO;
import com.github.javafaker.Faker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class InformationAdder implements Runnable {
    private FileReader CSVIn;
    private int offset;
    private int maxim;
    private int moviesID;
    private int directorsID;
    private Connection connection;
    private int size;

    public InformationAdder(int moviesID, int directorsID, Connection connection,int size){
            this.directorsID = directorsID;
            this.moviesID = moviesID;
            this.connection = connection;
            this.size = size;
    }

    @Override
    public void run() {
            DAO<Movie> movieAdder = new MovieDAO(connection);
            DAO<Director> directorAdder = new DirectorDAO(connection);
            Faker faker = new Faker();

            int maximum = 10000/size;

            for (int i = 0 ;i<maximum;i++) {
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
}
