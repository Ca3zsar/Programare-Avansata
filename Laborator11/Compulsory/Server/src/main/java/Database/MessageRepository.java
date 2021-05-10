package Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MessageRepository {
    private final EntityManagerFactory managerFactory;

    public MessageRepository(EntityManagerFactory newManagerFactory) {
        this.managerFactory = newManagerFactory;
    }

    public void create(MessageEntity object) {
        EntityManager manager = managerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();

        manager.close();
    }

    public List<MessageEntity> findAllMessages(String receiver) {
        EntityManager manager = managerFactory.createEntityManager();
        List<MessageEntity> result;
        result = manager.createNamedQuery("findAllMessages", MessageEntity.class).setParameter("receiver", receiver).
                getResultList();

        manager.close();
        return result;
    }
}
