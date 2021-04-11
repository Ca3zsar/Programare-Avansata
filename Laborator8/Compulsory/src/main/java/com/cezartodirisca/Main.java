package com.cezartodirisca;

import Classes.Genre;
import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.GenreDAO;
import DAOClasses.MovieDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = JDBCConnection.getInstance().getConnection();

            DAO<Movie> movieManager = new MovieDAO(connection);
            DAO<Genre> genreManager = new GenreDAO(connection);

            movieManager.insert(1,new Movie("The Shawshank Redemption","1994-10-14",144,9.3));
            movieManager.insert(2,new Movie("The Godfather","1972-03-24",175,9.2));
            movieManager.insert(3,new Movie("The Dark Knight","2008-07-18",152,9.0));

            genreManager.insert(1,new Genre("Comedy"));
            genreManager.insert(2,new Genre("Thriller"));
            genreManager.insert(3,new Genre("Crime"));

            List<Movie> results = movieManager.getAll();
            for(Movie movie:results)
            {
                System.out.println(movie);
            }

            System.out.println("---------");

            Movie result = movieManager.getById(2);
            System.out.println(result);

            System.out.println("---------");

            results = movieManager.getByName("The Godfather");
            for(Movie movie:results)
            {
                System.out.println(movie);
            }

            movieManager.update(2,new Movie("The Godmother","1972-03-24",175,9.2));

            System.out.println("---------");
            results = movieManager.getAll();
            for(Movie movie:results)
            {
                System.out.println(movie);
            }

            movieManager.delete(1);
            System.out.println("---------");
            results = movieManager.getAll();
            for(Movie movie:results)
            {
                System.out.println(movie);
            }

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
