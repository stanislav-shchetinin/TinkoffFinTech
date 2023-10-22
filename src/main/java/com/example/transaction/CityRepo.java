package com.example.transaction;

import com.example.entities.CityEntity;
import com.example.entities.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepo extends CrudRepository<CityEntity, Integer> {
    Optional<CityEntity> getByName(String name);
}
