package id.ac.ui.cs.advprog.eshop.repository;
import java.util.Iterator;

public interface BaseRepository<T> {
  public T create(T model);

  public Iterator<T> findAll();

  public T findById(String modelId);

  public T update(T model);

  public T delete(String modelId);
}
