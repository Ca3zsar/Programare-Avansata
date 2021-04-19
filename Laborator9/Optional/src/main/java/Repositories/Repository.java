package Repositories;

import java.util.List;

public interface Repository<T> {
    void create(T object);
    T findById(long id);
    List<T> findByName(String name);
}
