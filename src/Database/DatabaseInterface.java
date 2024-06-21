package Database;

import java.util.List;

public interface DatabaseInterface<T> {
    List<T> getAll();
    T getById(int id);
    void insert(T entity);
    void delete(int id);
    void update(int id,String column, Object value);
}
