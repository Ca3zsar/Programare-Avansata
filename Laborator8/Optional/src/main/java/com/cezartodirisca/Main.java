package com.cezartodirisca;

import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.MovieDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Main {

    public static void main(String[] args) {
        try {
            ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());
            Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator8\\movies.sql"));

            scriptRunner.runScript(reader);

            DAO<Movie> movieManager = new MovieDAO(JDBCConnection.getInstance().getConnection());

            InformationTool importer = new InformationTool("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator8\\imdb_movies.csv");
            importer.AddInformation(1,1);

//            List<Movie> movies = movieManager.getAll();
//            for(Movie movie:movies)
//            {
//                System.out.println(movie);
//            }

            //Display the directors and the movies they directed
            List<List<String>> directed = importer.directedMovies();
            for(List<String> row:directed)
            {
                System.out.println(row.get(0) + " - " + row.get(1));
            }

        } catch (SQLException | ClassNotFoundException | FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
