package com.example.dao;

import com.example.entities.WeatherEntity;

import java.util.List;
import java.util.Optional;

public class WeatherDaoJDBC implements Dao<WeatherEntity> {
    @Override
    public Optional<WeatherEntity> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<WeatherEntity> getAll() {
        return null;
    }

    @Override
    public void save(WeatherEntity weatherEntity) {

    }

    @Override
    public void update(WeatherEntity weatherEntity, String[] params) {

    }

    @Override
    public void delete(WeatherEntity weatherEntity) {

    }
}
