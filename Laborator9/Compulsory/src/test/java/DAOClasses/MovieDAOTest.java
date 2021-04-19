package DAOClasses;

import JPAEntitites.MovieEntity;
import com.cezartodirisca.JDBCConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MovieDAOTest {
    DAO<MovieEntity> movieAdder;
    @Before
    public void setUp() throws SQLException {
        try {

            movieAdder = new MovieDAO(JDBCConnection.getInstance().getConnection());
            movieAdder.insert(1001,new MovieEntity("Test1",new Date(),120,9.9));
            movieAdder.insert(1002,new MovieEntity("Test2",new Date(),200,5.3));
            movieAdder.insert(1003,new MovieEntity("Test2",new Date(),220,6.5));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void firstTest()
    {
        List<MovieEntity> results;
        results = movieAdder.getByName("Test2");
        Assert.assertEquals(2,results.size());
    }

    @Test
    public void secondTest() {
        movieAdder.delete(1001);
        List<MovieEntity> results;
        results = movieAdder.getAll();
        Assert.assertEquals(2,results.size());
    }

    @After
    public void tearDown() {
        movieAdder.delete(1002);
        movieAdder.delete(1003);
    }
}