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
import java.util.ArrayList;
import java.util.List;

public class InformationTool {
    private final Connection connection;
    private Reader CSVIn;

    public InformationTool(Connection connection,String fileName)
    {
        this.connection = connection;
        try {
            this.CSVIn = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddInformation(int moviesID, int directorsID)
    {
        List<String>toReturn = new ArrayList<>();
        try(CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(CSVIn);) {
            DAO<Movie> movieAdder = new MovieDAO(connection);
            DAO<Director> directorAdder = new DirectorDAO(connection);
            for(CSVRecord record : csvParser)
            {
                String title = record.get("title");
                String date = record.get("date_published");
                String duration = record.get("duration");
                String score = record.get("avg_vote");
                String director = record.get("director");

                
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
