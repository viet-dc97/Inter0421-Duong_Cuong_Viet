package model.dao;

import java.sql.ResultSet;
import java.util.List;

public interface IDao<T, KeyType> {
    void insert(T entity);
    void update(T entity);
    void delete(KeyType id);
    T selectById(KeyType id);
    List<T> findAll();
    List<T> selectBySql(String sqlString, Object...argsObjects);
    T readFromResultSet(ResultSet rs);
}
