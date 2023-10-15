package com.example.dao;

import com.example.entities.WeatherEntity;
import com.example.util.WeatherEntityORM;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.Optional;

@Component
public class WeatherDaoJDBC implements Dao<WeatherEntity> {
    private final HikariDataSource h2DataSource;
    private final WeatherEntityORM weatherEntityORM;
    public WeatherDaoJDBC(@Qualifier("h2-jdbc") HikariDataSource h2DataSource,
                          @Autowired WeatherEntityORM weatherEntityORM){
        this.h2DataSource = h2DataSource;
        this.weatherEntityORM = weatherEntityORM;
        delete(1);
    }
    @Override
    public Optional<WeatherEntity> get(int id) {
        try (PreparedStatement preparedStatement =
                     h2DataSource.getConnection().prepareStatement(String.format("select * from weather where id = %d", id))){
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                WeatherEntity weatherEntity = weatherEntityORM.mapResultSetToWeatherEntity(resultSet);
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
        try (PreparedStatement preparedStatement =
                     h2DataSource
                             .getConnection()
                             .prepareStatement(weatherEntityORM.generateSqlQueryForAddingWeatherEntity(weatherEntity))){
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
