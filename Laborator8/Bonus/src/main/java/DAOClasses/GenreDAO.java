package DAOClasses;

import Classes.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements DAO<Genre> {
    private final Connection connection;

    public GenreDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Genre getById(int id)
    {
        ResultSet results;
        Genre toReturn = null;
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM genres WHERe id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(1);
                toReturn = new Genre(name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<Genre> getByName(String genre)
    {
        ResultSet results;
        List<Genre> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM genres WHERE name="))
        {
            prepared.setString(1,genre);
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(1);
                toReturn.add(new Genre(name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<Genre> getAll()
    {
        ResultSet results;
        List<Genre> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM genres"))
        {
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(1);
                toReturn.add(new Genre(name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,Genre newGenre)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO genres VALUES(?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newGenre.getName());

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(int id,Genre genre)
    {
        try(PreparedStatement prepared = connection.prepareStatement("UPDATE genres SET id=?, name=? WHERE id= ?"))
        {
            prepared.setInt(1, id);
            prepared.setString(2,genre.getName());
            prepared.setInt(3,id);

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
        try(PreparedStatement prepared = connection.prepareStatement("DELETE FROM genres WHERE id= ?"))
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
