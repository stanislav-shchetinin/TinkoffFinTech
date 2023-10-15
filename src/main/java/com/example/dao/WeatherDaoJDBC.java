package com.example.dao;

import com.example.entities.WeatherEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.annotations.ResultCheckStyle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class WeatherDaoJDBC implements Dao<WeatherEntity> {
    private final HikariDataSource h2DataSource;
    public WeatherDaoJDBC(@Qualifier("h2-jdbc") HikariDataSource h2DataSource){
        this.h2DataSource = h2DataSource;
        get(1);
    }
    @Override
    public Optional<WeatherEntity> get(long id) {
        try (PreparedStatement preparedStatement =
                     h2DataSource.getConnection().prepareStatement(String.format("select * from weather were id = %d", id))){
            ResultSet resultSet = preparedStatement.executeQuery();
            WeatherEntity weatherEntity = new WeatherEntity();
        } catch (SQLException e){

        }
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
