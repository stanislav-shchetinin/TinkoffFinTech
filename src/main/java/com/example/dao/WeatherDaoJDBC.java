package com.example.dao;

import com.example.entities.WeatherEntity;
import com.example.util.WeatherEntityORM;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Component
public class WeatherDaoJDBC implements Dao<WeatherEntity> {
    private final HikariDataSource h2DataSource;
    public WeatherDaoJDBC(@Qualifier("h2-jdbc") HikariDataSource h2DataSource){
        this.h2DataSource = h2DataSource;
    }
    @Override
    public Optional<WeatherEntity> get(int id) {
        try (PreparedStatement preparedStatement =
                     h2DataSource.getConnection().prepareStatement(String.format("select * from weather where id = %d", id))){
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                WeatherEntity weatherEntity = WeatherEntityORM.mapResultSetToWeatherEntity(resultSet);
                System.out.println(weatherEntity);
                return Optional.ofNullable(weatherEntity);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
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
