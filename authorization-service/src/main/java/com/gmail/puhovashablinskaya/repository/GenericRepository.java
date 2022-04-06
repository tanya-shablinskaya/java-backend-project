package com.gmail.puhovashablinskaya.repository;

import java.util.List;

public interface GenericRepository<I, T> {
    void add(T entity);

    T update(T entity);

    T findById(I id);

    List<T> findAll();
}
