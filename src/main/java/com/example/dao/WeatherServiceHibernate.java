package com.example.dao;

import com.example.entities.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherServiceHibernate implements Dao<WeatherEntity> {

    private final WeatherRepoHibernate weatherRepoHibernate;

    public WeatherServiceHibernate(@Autowired WeatherRepoHibernate weatherRepoHibernate){
        this.weatherRepoHibernate = weatherRepoHibernate;
    }
    @Override
    public Optional<WeatherEntity> get(int id) {
        return weatherRepoHibernate.findById(id);
    }

    @Override
    public void save(WeatherEntity weatherEntity) {
        weatherRepoHibernate.save(weatherEntity);
    }

    @Override
    public void update(WeatherEntity weatherEntity) {
        weatherRepoHibernate.save(weatherEntity);
    }

    @Override
    public void delete(int id) {
        weatherRepoHibernate.deleteById(id);
    }
}
