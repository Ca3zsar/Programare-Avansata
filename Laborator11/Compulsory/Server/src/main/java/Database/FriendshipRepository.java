package Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class FriendshipRepository {
    private final EntityManagerFactory managerFactory;

    public FriendshipRepository(EntityManagerFactory newManagerFactory) {
        this.managerFactory = newManagerFactory;
    }

    public void create(FriendshipEntity object) {
        EntityManager manager = managerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();

        manager.close();
    }

    public List<FriendshipEntity> findAllFriendships(String firstUser) {
        EntityManager manager = managerFactory.createEntityManager();
        List<FriendshipEntity> result;
        result = manager.createNamedQuery("findAllFriendships", FriendshipEntity.class).setParameter("firstUser", firstUser).
                getResultList();

        manager.close();
        return result;
    }
}
