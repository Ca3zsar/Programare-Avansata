package Repositories;

import JPAEntitites.MovieEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class MovieRepository implements Repository<MovieEntity> {
    private final EntityManagerFactory managerFactory;

    public MovieRepository(EntityManagerFactory newManagerFactory) {
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
        query.setParameter(1, id);
        MovieEntity result = (MovieEntity) query.getSingleResult();
        manager.close();

        return result;
    }

    @Override
    public List<MovieEntity> findByName(String name) {
        EntityManager manager = managerFactory.createEntityManager();

        List<MovieEntity> result = manager.createNamedQuery("findByTitle",MovieEntity.class).setParameter("name",name).
                getResultList();

        manager.close();
        return result;
    }
}
