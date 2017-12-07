package pl.eltrox.core.domain.repository;

import java.util.Collection;

public interface RepositoryInterface<T> {
    public T get(long id);
    public Collection<T> all();
    public T save(T entity);
    public int count();
}
