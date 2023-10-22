package com.example.transaction;

import com.example.entities.CityEntity;
import org.springframework.data.repository.CrudRepository;

public interface WeatherAPIRepo extends CrudRepository<WeatherAPIEntity, Integer> {
}
