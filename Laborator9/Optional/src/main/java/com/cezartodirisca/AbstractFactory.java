package com.cezartodirisca;

import DAOClasses.*;
import JPAEntitites.ActorEntity;
import JPAEntitites.DirectorEntity;
import JPAEntitites.GenreEntity;
import JPAEntitites.MovieEntity;
import Repositories.*;

import java.sql.SQLException;

public class AbstractFactory{
    /**
     *
     * @param argument That argument is only needed in order to get the type of DAO to return
     * @param <T> The type of the DAO to return
     * @return A DAO object : ActorDAO, MovieDAO, GenreDAO or DirectorDAO
     * @throws SQLException It's thrown if the JDBC connection cannot be established
     * @throws ClassNotFoundException It's thrown if the driver is not found.
     */
    public <T> DAO<T> getDAO(T argument) throws SQLException, ClassNotFoundException {
        if(argument instanceof ActorEntity)
        {
            DAO<ActorEntity> toReturn = new ActorDAO(JDBCConnection.getInstance().getConnection());
            return (DAO<T>) toReturn;
        }
        if(argument instanceof DirectorEntity)
        {
            DAO<DirectorEntity> toReturn = new DirectorDAO(JDBCConnection.getInstance().getConnection());
            return (DAO<T>) toReturn;
        }
        if(argument instanceof MovieEntity)
        {
            DAO<MovieEntity> toReturn = new MovieDAO(JDBCConnection.getInstance().getConnection());
            return (DAO<T>) toReturn;
        }
        if(argument instanceof GenreEntity)
        {
            DAO<GenreEntity> toReturn = new GenreDAO(JDBCConnection.getInstance().getConnection());
            return (DAO<T>)toReturn;
        }
        return null;
    }

    /**
     *
     * @param argument That argument is only needed in order to get the type of Repository to return
     * @param <T> The type of the repository to return
     * @return a Repository : DirectorRepository, ActorRepository, MovieRepository or GenreRepository
     */
    public <T> Repository<T> getRepository(T argument)
    {
        if(argument instanceof ActorEntity)
        {
            Repository<ActorEntity> toReturn = new ActorRepository(Factory.getInstance().getEntityManagerFactory());
            return (Repository<T>) toReturn;
        }
        if(argument instanceof DirectorEntity)
        {
            Repository<DirectorEntity> toReturn = new DirectorRepository(Factory.getInstance().getEntityManagerFactory());
            return (Repository<T>) toReturn;
        }
        if(argument instanceof MovieEntity)
        {
            Repository<MovieEntity> toReturn = new MovieRepository(Factory.getInstance().getEntityManagerFactory());
            return (Repository<T>) toReturn;
        }
        if(argument instanceof GenreEntity)
        {
            Repository<GenreEntity> toReturn = new GenreRepository(Factory.getInstance().getEntityManagerFactory());
            return (Repository<T>)toReturn;
        }
        return null;
    }
}
