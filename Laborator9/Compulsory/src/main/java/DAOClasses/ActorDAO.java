package DAOClasses;

import JPAEntitites.ActorEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO implements DAO<ActorEntity>{
    private final Connection connection;

    public ActorDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public ActorEntity getById(int id)
    {
        ResultSet results;
        ActorEntity toReturn = new ActorEntity();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors WHERE id=?"))
        {
            prepared.setInt(1,id);
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = formatter.format(results.getDate(3));

                toReturn = new ActorEntity(name, formatter.parse(birthday));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<ActorEntity> getByName(String actorName)
    {
        ResultSet results;
        List<ActorEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors WHERE name=?"))
        {
            prepared.setString(1,actorName);
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = formatter.format(results.getDate(3));

                toReturn.add(new ActorEntity(name,formatter.parse(birthday)));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public List<ActorEntity> getAll()
    {
        ResultSet results;
        List<ActorEntity> toReturn = new ArrayList<>();
        try(PreparedStatement prepared = connection.prepareStatement("SELECT * FROM actors"))
        {
            results = prepared.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(results.next())
            {
                String name = results.getString(2);
                String birthday = formatter.format(results.getDate(3));
                toReturn.add(new ActorEntity(name,formatter.parse(birthday)));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public void insert(int id,ActorEntity newActor)
    {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO actors VALUES(?, ?, ?)")) {
            prepared.setInt(1, id);
            prepared.setString(2, newActor.getName());
            prepared.setDate(3, new java.sql.Date(newActor.getBirthday().getTime()));

            prepared.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(int id,ActorEntity newActor)
    {
        try(PreparedStatement prepared = connection.prepareStatement("UPDATE movies SET id=?, name=?, birthday=? WHERE id= ?"))
        {
            prepared.setInt(1, id);
            prepared.setString(2, newActor.getName());
            prepared.setDate(3, new java.sql.Date(newActor.getBirthday().getTime()));
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
