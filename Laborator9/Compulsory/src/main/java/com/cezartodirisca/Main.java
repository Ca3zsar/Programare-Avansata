package com.cezartodirisca;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import JPAEntitites.MovieEntity;
import Repositories.MovieRepository;
import Repositories.Repository;
import freemarker.template.TemplateException;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        try {
//            ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());
//            Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\movies.sql"));
//
//            scriptRunner.runScript(reader);

            InformationTool info = new InformationTool("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\imdb_movies.csv",
                                                        Factory.getInstance().getEntityManagerFactory());
//            info.AddInformation();
                info.report();
//            Factory factory = Factory.getInstance();
//            EntityManagerFactory managerFactory = factory.getEntityManagerFactory();
//
//            Repository<MovieEntity> movieRepository = new MovieRepository(managerFactory);
//            for(int i=0;i<10;i++)
//            {
//                MovieEntity movie = new MovieEntity("title"+i,new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"),i*100,i);
//                movieRepository.create(movie);
//            }
//
//            managerFactory.close();
        } catch (SQLException | ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
