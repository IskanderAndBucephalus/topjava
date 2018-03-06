package ru.javawebinar.topjava.dao;

import java.util.Collection;

public interface ParentRepository<T> {
    Collection<T> getAll();
    T save(T inst);
    void update(T inst);
    void delete(Integer id);
    T get(Integer id);
    }
