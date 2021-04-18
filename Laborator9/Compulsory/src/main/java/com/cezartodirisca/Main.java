package com.cezartodirisca;

import Classes.Movie;
import DAOClasses.DAO;
import DAOClasses.MovieDAO;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import JPAEntitites.MovieEntity;
import Repositories.MovieRepository;
import Repositories.Repository;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        try {
            ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());
            Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\movies.sql"));

            scriptRunner.runScript(reader);

            DAO<Movie> movieManager = new MovieDAO(JDBCConnection.getInstance().getConnection());
            Factory factory = Factory.getInstance();
            EntityManagerFactory managerFactory = factory.getEntityManagerFactory();

            Repository<MovieEntity> movieRepository = new MovieRepository(managerFactory);
            MovieEntity movie = new MovieEntity();
            movie.setTitle("titlu ceva");
            movie.setDuration(10);
            movie.setScore(9);
            movie.setReleaseDate(new SimpleDateFormat("dd.MM.yyyy")
                    .parse("01.01.1991"));
            movieRepository.create(movie);

            movie = new MovieEntity();
            movie.setTitle("Abracadabra");
            movie.setDuration(10);
            movie.setScore(9);
            movie.setReleaseDate(new SimpleDateFormat("dd.MM.yyyy")
                    .parse("01.01.1991"));
            movie.setDuration(100000);
            movieRepository.create(movie);

            MovieEntity toGet = movieRepository.findById(2);
            System.out.println(toGet.getTitle());

        } catch (SQLException | ClassNotFoundException | IOException | ParseException exception) {
            exception.printStackTrace();
        }
    }
}
