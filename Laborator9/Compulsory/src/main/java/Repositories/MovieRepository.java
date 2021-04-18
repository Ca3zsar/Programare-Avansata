package Repositories;

import Classes.Movie;
import JPAEntitites.MovieEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class MovieRepository implements Repository<MovieEntity>{
    private EntityManagerFactory managerFactory;

    public MovieRepository(EntityManagerFactory newManagerFactory)
    {
        this.managerFactory = newManagerFactory;
    }

    @Override
    public void create(MovieEntity object) {
        EntityManager manager = managerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public MovieEntity findById(long id) {
        EntityManager manager = managerFactory.createEntityManager();

        String sqlQuery = "select * from movies where id = ?";
        Query query = manager.createNativeQuery(sqlQuery, MovieEntity.class);
        query.setParameter(1,id);
        MovieEntity result = (MovieEntity) query.getSingleResult();
        return result;
    }

    @Override
    public MovieEntity findByName(String name) {
        return null;
    }
}
