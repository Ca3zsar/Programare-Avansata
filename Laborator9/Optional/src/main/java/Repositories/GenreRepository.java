package Repositories;

import JPAEntitites.GenreEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class GenreRepository implements Repository<GenreEntity> {
    private final EntityManagerFactory managerFactory;

    public GenreRepository(EntityManagerFactory newManagerFactory)
    {
        this.managerFactory = newManagerFactory;
    }

    @Override
    public void create(GenreEntity object) {
        EntityManager manager = managerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();

        manager.close();
    }

    @Override
    public GenreEntity findById(long id) {
        EntityManager manager = managerFactory.createEntityManager();

        String sqlQuery = "select * from genres where id = ?";
        Query query = manager.createNativeQuery(sqlQuery, GenreEntity.class);
        query.setParameter(1,id);

        GenreEntity result = (GenreEntity) query.getSingleResult();

        manager.close();

        return result;
    }

    @Override
    public List<GenreEntity> findByName(String name) {
        EntityManager manager = managerFactory.createEntityManager();

        List<GenreEntity> result = manager.createNamedQuery("findByGenreName",GenreEntity.class).setParameter("name",name).
                getResultList();

        manager.close();
        return result;
    }
}
