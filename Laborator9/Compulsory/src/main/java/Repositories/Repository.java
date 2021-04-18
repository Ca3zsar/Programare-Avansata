package Repositories;

public interface Repository<T> {
    public void create(T object);
    public T findById(long id);
    public T findByName(String name);
}
