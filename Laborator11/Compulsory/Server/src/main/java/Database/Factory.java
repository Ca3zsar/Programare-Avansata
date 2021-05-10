package Database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {
    private static Factory managerFactory;
    private static EntityManagerFactory entityManagerFactory;

    private Factory(){}

    public static Factory getInstance()
    {
        if(managerFactory == null)
        {
            managerFactory = new Factory();
        }
        return managerFactory;
    }

    public EntityManagerFactory getEntityManagerFactory()
    {
        if(entityManagerFactory == null)
        {
            entityManagerFactory = Persistence.createEntityManagerFactory("MoviesPersistenceUnit");
        }
        return entityManagerFactory;
    }
}
