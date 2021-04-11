package DAOClasses;

import Classes.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements DAO<Movie>{
    private final Connection connection;

    public MovieDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Movie getById(int id)
    {
        ResultSet results;
        Movie toReturn = null;
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM movies WHERE id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            while(results.next())
            {
                String title = results.getString(2);
                String release = new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3));
                int duration = results.getInt(4);
                double score = results.getDouble(5);

                toReturn = new Movie(title,release,duration,score);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<Movie> getByName(String name)
    {
        ResultSet results;
        List<Movie> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM movies WHERE title=?"))
        {
            prepared.setString(1,name);
            results = prepared.executeQuery();
            while(results.next())
            {
                String title = results.getString(2);
                String release = new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3));
                int duration = results.getInt(4);
                double score = results.getDouble(5);

                toReturn.add(new Movie(title,release,duration,score));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<Movie> getAll()
    {
        ResultSet results;
        List<Movie> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM movies"))
        {
            results = prepared.executeQuery();
            while(results.next())
            {
                String title = results.getString(2);
                String release = new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3));
                int duration = results.getInt(4);
                double score = results.getDouble(5);

                toReturn.add(new Movie(title,release,duration,score));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,Movie newMovie)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO movies VALUES(?, ?, ?, ?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newMovie.getTitle());
            prepared.setDate(3, new java.sql.Date(newMovie.getReleaseDate().getTime()));
            prepared.setInt(4, newMovie.getDuration());
            prepared.setDouble(5, newMovie.getScore());

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(int id,Movie newMovie)
    {
        try(PreparedStatement prepared = connection.prepareStatement("UPDATE movies SET id=?, title=?, release_date=?, duration=?, score=? " +
                                                                        "WHERE id= ?"))
        {
            prepared.setInt(1, id);
            prepared.setString(2, newMovie.getTitle());
            prepared.setDate(3, new java.sql.Date(newMovie.getReleaseDate().getTime()));
            prepared.setInt(4, newMovie.getDuration());
            prepared.setDouble(5, newMovie.getScore());
            prepared.setInt(6,id);

            int rowsUpdated = prepared.executeUpdate();
            if(rowsUpdated == 0)
            {
                System.out.println("No rows updated!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void delete(int id)
    {
        try(PreparedStatement prepared = connection.prepareStatement("DELETE from movies WHERE id= ?"))
        {
            prepared.setInt(1, id);

            int rowsUpdated = prepared.executeUpdate();
            if(rowsUpdated == 0)
            {
                System.out.println("No rows updated!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
