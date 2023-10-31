package com.example.dao;

import com.example.entities.WeatherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepoHibernate extends CrudRepository<WeatherEntity, Integer> {

}
