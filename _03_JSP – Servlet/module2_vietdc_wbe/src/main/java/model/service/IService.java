package model.service;

import java.util.List;

public interface IService<T, KeyType> {
    void insert(T entity);
    void update(T entity);
    void delete(KeyType id);
    T selectById(KeyType id);
    List<T> findAll();
}
