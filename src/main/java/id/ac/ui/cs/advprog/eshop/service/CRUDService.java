package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface CRUDService<T> {
  public T create(T model);

  public T findById(String modelId);

  public List<T> findAll();

  public T update(T model);

  public T delete(String modelId);
}
