package DAOClasses;

import Classes.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO implements DAO<Director>{
    private final Connection connection;

    public DirectorDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public Director getById(int id)
    {
        ResultSet results;
        Director toReturn = null;
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors WHERE id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3));

                toReturn = new Director(name, birthday);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<Director> getByName(String actorName)
    {
        ResultSet results;
        List<Director> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors WHERE name=?"))
        {
            prepared.setString(1,actorName);
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3));

                toReturn.add(new Director(name,birthday));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<Director> getAll()
    {
        ResultSet results;
        List<Director> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors"))
        {
            results = prepared.executeQuery();
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3));
                toReturn.add(new Director(name,birthday));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,Director newDirector)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO actors VALUES(?, ?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newDirector.getName());
            prepared.setDate(3, new java.sql.Date(newDirector.getBirthday().getTime()));

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(int id,Director newDirector)
    {
        try(PreparedStatement prepared = connection.prepareStatement("UPDATE movies SET id=?, name=?, birthday=? WHERE id= ?"))
        {
            prepared.setInt(1, id);
            prepared.setString(2, newDirector.getName());
            prepared.setDate(3, new java.sql.Date(newDirector.getBirthday().getTime()));
            prepared.setInt(4,id);

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
