package com.example.transaction;

import com.example.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface WeatherAPIRepo extends JpaRepository<WeatherAPIEntity, Integer> {
    WeatherAPIEntity findByCityAndTimestamp(CityEntity city, Timestamp timestamp);
}
