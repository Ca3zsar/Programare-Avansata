package com.cezartodirisca;

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

            Factory factory = Factory.getInstance();
            EntityManagerFactory managerFactory = factory.getEntityManagerFactory();

            Repository<MovieEntity> movieRepository = new MovieRepository(managerFactory);
            for(int i=0;i<10;i++)
            {
                MovieEntity movie = new MovieEntity("title"+i,new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"),i*100,i);
                movieRepository.create(movie);
            }

            managerFactory.close();
        } catch (SQLException | ClassNotFoundException | IOException | ParseException exception) {
            exception.printStackTrace();
        }
    }
}
