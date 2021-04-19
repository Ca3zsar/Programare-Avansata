package Repositories;

import JPAEntitites.ActorEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class ActorRepository implements Repository<ActorEntity> {
    private final EntityManagerFactory managerFactory;

    public ActorRepository(EntityManagerFactory newManagerFactory)
    {
        this.managerFactory = newManagerFactory;
    }

    @Override
    public void create(ActorEntity object) {
        EntityManager manager = managerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();

        manager.close();
    }

    @Override
    public ActorEntity findById(long id) {
        EntityManager manager = managerFactory.createEntityManager();

        String sqlQuery = "select * from actors where id = ?";
        Query query = manager.createNativeQuery(sqlQuery,ActorEntity.class);
        query.setParameter(1,id);

        ActorEntity result = (ActorEntity) query.getSingleResult();

        manager.close();

        return result;
    }

    @Override
    public List<ActorEntity> findByName(String name) {
        EntityManager manager = managerFactory.createEntityManager();

        List<ActorEntity> result = manager.createNamedQuery("findByActorName",ActorEntity.class).setParameter("name",name).
                                    getResultList();

        manager.close();
        return result;
    }
}
