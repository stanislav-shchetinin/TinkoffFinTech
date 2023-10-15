package com.example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}