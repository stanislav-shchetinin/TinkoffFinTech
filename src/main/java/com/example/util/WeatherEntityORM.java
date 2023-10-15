package com.example.util;

import com.example.entities.WeatherEntity;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherEntityORM {
    public static WeatherEntity mapResultSetToWeatherEntity(ResultSet resultSet) throws SQLException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        JdbcORM<WeatherEntity> jdbcORM = new JdbcORM<>();
        return jdbcORM.mapResultSetToClass(resultSet, WeatherEntity.class);
    }
}
