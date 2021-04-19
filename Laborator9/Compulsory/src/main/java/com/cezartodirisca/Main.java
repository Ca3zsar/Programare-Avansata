package com.cezartodirisca;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import DAOClasses.DAO;
import JPAEntitites.DirectorEntity;
import JPAEntitites.MovieEntity;
import Repositories.Repository;
import freemarker.template.TemplateException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        //Read the initialization file
        String optionString = "DAO"; // The default option will be DAO, in case the file is not available
        Path fileName = Path.of("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\initialize.json");
        try {
            String content = Files.readString(fileName);
            JSONParser parser = new JSONParser();
            Object jsonContent = parser.parse(content);

            JSONObject option = (JSONObject) jsonContent;
            optionString = (String)option.get("type");
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        // Create the tool to import and display the IMDB data
        InformationTool info = new InformationTool("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\imdb_movies.csv",
                                                    Factory.getInstance().getEntityManagerFactory());

        try {
            info.report();
        } catch (IOException | TemplateException | SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        // Get the instance of the Factory singleton
        Factory factory = Factory.getInstance();
        EntityManagerFactory managerFactory = factory.getEntityManagerFactory();

        //Create an abstract factory which returns either a DAO or a Repository
        AbstractFactory abstractFactory = new AbstractFactory();

        //Check the option
        if(optionString.equals("DAO")) {
            DAO<DirectorEntity> directorGetter;
            try {
                directorGetter = abstractFactory.getDAO(new DirectorEntity());
                DirectorEntity director = directorGetter.getById(10);
                System.out.println(director.getName());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else if(optionString.equals("Repository"))
        {
            Repository<MovieEntity> movieRep = abstractFactory.getRepository(new MovieEntity());

            MovieEntity movie = movieRep.findById(15);
            System.out.println(movie.getTitle());
        }
        managerFactory.close();
    }
}
