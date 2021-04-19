package DAOClasses;

import JPAEntitites.GenreEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements DAO<GenreEntity> {
    private final Connection connection;

    public GenreDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public GenreEntity getById(int id)
    {
        ResultSet results;
        GenreEntity toReturn = new GenreEntity();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM genres WHERe id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(1);
                toReturn = new GenreEntity(name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<GenreEntity> getByName(String genre)
    {
        ResultSet results;
        List<GenreEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM genres WHERE name="))
        {
            prepared.setString(1,genre);
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(1);
                toReturn.add(new GenreEntity(name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<GenreEntity> getAll()
    {
        ResultSet results;
        List<GenreEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM genres"))
        {
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(1);
                toReturn.add(new GenreEntity(name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,GenreEntity newGenreEntity)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO genres VALUES(?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newGenreEntity.getName());

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(int id,GenreEntity genre)
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
