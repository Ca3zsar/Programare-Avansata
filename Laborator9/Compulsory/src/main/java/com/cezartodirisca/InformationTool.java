package com.cezartodirisca;

import JPAEntitites.DirectorEntity;
import JPAEntitites.MovieEntity;
import Repositories.DirectorRepository;
import Repositories.MovieRepository;
import Repositories.Repository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class InformationTool {
    private Reader CSVIn;
    private final EntityManagerFactory managerFactory;

    public InformationTool(String fileName,EntityManagerFactory managerFactory)
    {
        try {
            this.CSVIn = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.managerFactory = managerFactory;
    }

    public void AddInformation() {
        EntityManager entityManager = managerFactory.createEntityManager();
        Repository<MovieEntity> movieAdder = new MovieRepository(managerFactory);
        Repository<DirectorEntity> directorAdder = new DirectorRepository(managerFactory);

        try(CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(CSVIn)) {
            Pattern pattern = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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

                movieAdder.create(new MovieEntity(title,format.parse(date),Integer.parseInt(duration),Double.parseDouble(score)));
                directorAdder.create(new DirectorEntity(director,format.parse("01-01-1900")));
            }
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
    }

    public void report() throws IOException, TemplateException, SQLException, ClassNotFoundException {
        Configuration configuration = new Configuration();

        configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\Optional\\src\\resources"));

        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Map<String, Object> input = new HashMap<>();

        input.put("title", "Data Information");
        input.put("dateCreated",formatter.format(new Date()));

        EntityManager manager = managerFactory.createEntityManager();

        String sqlQuery = "select * from movies order by score desc";
        Query query = manager.createNativeQuery(sqlQuery, MovieEntity.class);

        List<MovieEntity> result = query.getResultList();
        manager.close();

        List<String> entriesDescription = new ArrayList<>();
        for(MovieEntity movie : result){
            entriesDescription.add(movie.getTitle() + " : " + movie.getScore());
        }
        // Get the info from the database

        input.put("entries", entriesDescription);

        Template template = configuration.getTemplate("report.ftl");

        try (Writer fileWriter = new FileWriter("output.html")) {
            template.process(input, fileWriter);

            File entryFile = new File("output.html");
            Desktop desktop = Desktop.getDesktop();

            desktop.open(entryFile);
        }
    }
}
