package DAOClasses;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    T getById(int id);

    List<T> getByName(String name);

    List<T> getAll();

    void insert(int id,T newObject);

    void update(int id,T object);

    void delete(int id);
}
