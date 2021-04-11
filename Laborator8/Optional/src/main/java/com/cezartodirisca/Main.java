package com.cezartodirisca;

import Classes.Genre;
import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.GenreDAO;
import DAOClasses.MovieDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = JDBCConnection.getInstance().getConnection();

            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator8\\movies.sql"));

            DAO<Movie> movieManager = new MovieDAO(connection);
            DAO<Genre> genreManager = new GenreDAO(connection);

            InformationTool importer = new InformationTool(connection,"C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator8\\imbd_movies.csv");
        } catch (SQLException | ClassNotFoundException | FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
