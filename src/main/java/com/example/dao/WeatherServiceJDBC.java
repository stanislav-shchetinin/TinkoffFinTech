package com.example.dao;

import com.example.entities.WeatherEntity;
import com.example.util.WeatherEntityORM;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Service
@Slf4j
public class WeatherServiceJDBC implements Dao<WeatherEntity> {
    private final HikariDataSource h2DataSource;
    private final WeatherEntityORM weatherEntityORM;
    public WeatherServiceJDBC(@Qualifier("h2-jdbc") HikariDataSource h2DataSource,
                              @Autowired WeatherEntityORM weatherEntityORM){
        this.h2DataSource = h2DataSource;
        this.weatherEntityORM = weatherEntityORM;
    }
    @Override
    public Optional<WeatherEntity> get(int id) {
        try (PreparedStatement preparedStatement =
                     h2DataSource.getConnection().prepareStatement(String.format("select * from weather where id = %d", id))){
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            WeatherEntity weatherEntity = weatherEntityORM.mapResultSetToWeatherEntity(resultSet);
            return Optional.ofNullable(weatherEntity);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public void save(WeatherEntity weatherEntity) {
        try (PreparedStatement preparedStatement =
                     h2DataSource
                             .getConnection()
                             .prepareStatement(weatherEntityORM.generateSqlQueryForAddingWeatherEntity(weatherEntity))){
            preparedStatement.execute();
        } catch (Exception e){
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Берет id из weatherEntity и обновляет у записи с соответвующим id все поля на поля из weatherEntity
     * */
    @Override
    public void update(WeatherEntity weatherEntity) {
        try (PreparedStatement preparedStatement =
                     h2DataSource
                             .getConnection()
                             .prepareStatement(weatherEntityORM.generateSqlQueryForUpdatingWeatherEntity(weatherEntity))) {
            preparedStatement.executeUpdate();
        } catch (Exception e){
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement =
                     h2DataSource
                             .getConnection()
                             .prepareStatement(String.format("delete from weather where id = %d", id))) {
            preparedStatement.executeUpdate();
        } catch (Exception e){
            log.warn(e.getMessage(), e);
        }
    }
}
