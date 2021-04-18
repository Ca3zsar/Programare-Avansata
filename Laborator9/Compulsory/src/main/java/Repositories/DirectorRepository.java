package Repositories;

import JPAEntitites.DirectorEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class DirectorRepository implements Repository<DirectorEntity> {
    private final EntityManagerFactory managerFactory;

    public DirectorRepository(EntityManagerFactory newManagerFactory)
    {
        this.managerFactory = newManagerFactory;
    }

    @Override
    public void create(DirectorEntity object) {
        EntityManager manager = managerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();

        manager.close();
    }

    @Override
    public DirectorEntity findById(long id) {
        EntityManager manager = managerFactory.createEntityManager();

        String sqlQuery = "select * from directors where name = ?";
        Query query = manager.createNativeQuery(sqlQuery, DirectorEntity.class);
        query.setParameter(1,id);

        DirectorEntity result = (DirectorEntity) query.getSingleResult();

        manager.close();

        return result;
    }

    @Override
    public List<DirectorEntity> findByName(String name) {
        EntityManager manager = managerFactory.createEntityManager();

        List<DirectorEntity> result = manager.createNamedQuery("findByDirectorName",DirectorEntity.class).setParameter("name",name).
                getResultList();

        manager.close();
        return result;
    }
}
