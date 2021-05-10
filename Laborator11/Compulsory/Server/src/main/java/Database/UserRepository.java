package Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class UserRepository{
    private final EntityManagerFactory managerFactory;

    public UserRepository(EntityManagerFactory newManagerFactory)
    {
        this.managerFactory = newManagerFactory;
    }

    public void create(UserEntity object) {
        EntityManager manager = managerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();

        manager.close();
    }

    public UserEntity findByName(String name) {
        EntityManager manager = managerFactory.createEntityManager();
        UserEntity result;
        try {
            result = manager.createNamedQuery("findByUsername", UserEntity.class).setParameter("username", name).getSingleResult();
        }catch(NoResultException nre)
        {
            result=null;
        }
        manager.close();
        return result;
    }
}
