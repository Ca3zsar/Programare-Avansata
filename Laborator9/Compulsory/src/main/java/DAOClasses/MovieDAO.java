package DAOClasses;

import JPAEntitites.MovieEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements DAO<MovieEntity>{
    private final Connection connection;

    public MovieDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public MovieEntity getById(int id)
    {
        ResultSet results;
        MovieEntity toReturn = new MovieEntity();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM movies WHERE id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String title = results.getString(2);
                String release = formatter.format(results.getDate(3));
                int duration = results.getInt(4);
                double score = results.getDouble(5);

                toReturn = new MovieEntity(title,formatter.parse(release),duration,score);
            }
            results.close();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<MovieEntity> getByName(String name)
    {
        ResultSet results;
        List<MovieEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM movies WHERE title=?"))
        {
            prepared.setString(1,name);
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String title = results.getString(2);
                String release = formatter.format(results.getDate(3));
                int duration = results.getInt(4);
                double score = results.getDouble(5);

                toReturn.add(new MovieEntity(title,formatter.parse(release),duration,score));
            }
            results.close();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<MovieEntity> getAll()
    {
        ResultSet results;
        List<MovieEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM movies"))
        {
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String title = results.getString(2);
                String release = formatter.format(results.getDate(3));
                int duration = results.getInt(4);
                double score = results.getDouble(5);

                toReturn.add(new MovieEntity(title,formatter.parse(release),duration,score));
            }
            results.close();
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,MovieEntity newMovieEntity)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO movies VALUES(?, ?, ?, ?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newMovieEntity.getTitle());
            prepared.setDate(3, new java.sql.Date(newMovieEntity.getReleaseDate().getTime()));
            prepared.setInt(4, newMovieEntity.getDuration());
            prepared.setDouble(5, newMovieEntity.getScore());

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(int id,MovieEntity newMovieEntity)
    {
        try(PreparedStatement prepared = connection.prepareStatement("UPDATE movies SET id=?, title=?, release_date=?, duration=?, score=? " +
                                                                        "WHERE id= ?"))
        {
            prepared.setInt(1, id);
            prepared.setString(2, newMovieEntity.getTitle());
            prepared.setDate(3, new java.sql.Date(newMovieEntity.getReleaseDate().getTime()));
            prepared.setInt(4, newMovieEntity.getDuration());
            prepared.setDouble(5, newMovieEntity.getScore());
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
