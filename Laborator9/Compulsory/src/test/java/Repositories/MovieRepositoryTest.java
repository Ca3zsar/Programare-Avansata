package Repositories;

import JPAEntitites.MovieEntity;
import com.cezartodirisca.Factory;
import com.cezartodirisca.JDBCConnection;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class MovieRepositoryTest {
    Repository<MovieEntity> movieManager;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    @Before
    public void setUp() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());
        Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\movies.sql"));

        scriptRunner.runScript(reader);

        movieManager = new MovieRepository(Factory.getInstance().getEntityManagerFactory());



        movieManager.create(new MovieEntity("The Shawshank Redemption",format.parse("14-10-1994"),142,9.3));
        movieManager.create(new MovieEntity("The Godfather",format.parse("24-03-1972"),175,9.2));
        movieManager.create(new MovieEntity("The Dark Knight",format.parse("18-07-2008"),152,9.0));
        movieManager.create(new MovieEntity("12 Angry Men",format.parse("10-04-1957"),96,9.0));
        movieManager.create(new MovieEntity("Schindler's List",format.parse("04-02-1994"),195,8.9));
        movieManager.create(new MovieEntity("repeatedName",format.parse("04-02-1994"),195,8.9));
        movieManager.create(new MovieEntity("repeatedName",format.parse("04-02-1994"),195,8.9));
    }

    @Test
    public void firstTest()
    {
        MovieEntity movie = movieManager.findById(4);
        assertEquals(movie.getTitle(),"12 Angry Men");
    }

    @Test
    public void secondTest()
    {
        List<MovieEntity> movies = movieManager.findByName("repeatedName");
        assertEquals(movies.size(),2);
    }

    @Test
    public void thirdTest()
    {
        try {
            movieManager.create(new MovieEntity("newName",format.parse("04-02-1994"),195,8.9));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MovieEntity movie = movieManager.findById(8);
        assertEquals(movie.getTitle(),"newName");
    }

    @After
    public void tearDown() {
        try {
            Reader reader;
            ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());

            reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator9\\movies.sql"));
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}