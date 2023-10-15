package com.example.util;

import com.example.entities.WeatherEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class WeatherEntityORM extends JdbcORM<WeatherEntity>{
    public WeatherEntity mapResultSetToWeatherEntity(ResultSet resultSet) throws
            SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return mapResultSetToClass(resultSet, WeatherEntity.class);
    }

    public String generateSqlQueryForAddingWeatherEntity(WeatherEntity weatherEntity) throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateSqlQueryForAdding(WeatherEntity.TABLE_NAME, weatherEntity);
    }

    public String generateSqlQueryForUpdatingWeatherEntity(WeatherEntity weatherEntity) throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateSqlQueryForUpdating(WeatherEntity.TABLE_NAME, weatherEntity);
    }

}
