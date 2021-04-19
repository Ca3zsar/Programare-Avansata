package DAOClasses;

import JPAEntitites.DirectorEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO implements DAO<DirectorEntity>{
    private final Connection connection;

    public DirectorDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public DirectorEntity getById(int id)
    {
        ResultSet results;
        DirectorEntity toReturn = new DirectorEntity();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM directors WHERE id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = formatter.format(results.getDate(3));

                toReturn = new DirectorEntity(name, formatter.parse(birthday));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<DirectorEntity> getByName(String actorName)
    {
        ResultSet results;
        List<DirectorEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors WHERE name=?"))
        {
            prepared.setString(1,actorName);
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = formatter.format(results.getDate(3));

                toReturn.add(new DirectorEntity(name,formatter.parse(birthday)));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<DirectorEntity> getAll()
    {
        ResultSet results;
        List<DirectorEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors"))
        {
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = formatter.format(results.getDate(3));
                toReturn.add(new DirectorEntity(name,formatter.parse(birthday)));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,DirectorEntity newDirector)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO directors VALUES(?, ?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newDirector.getName());
            prepared.setDate(3, new java.sql.Date(newDirector.getBirthday().getTime()));

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(int id,DirectorEntity newDirector)
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
