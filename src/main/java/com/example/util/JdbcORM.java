package com.example.util;

import com.example.entities.WeatherEntity;
import com.example.util.annotations.NotTableColumn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Класс для сопоставления записям БД и Java сущностям
 * */
@Component
public class JdbcORM<T> {
    /**
     * @param resultSet запись таблицы
     * @param clazz класс, объект которого требуется получить из записи resultSet
     *              у класса должен быть пустой public конструктор и public сеттеры,
     *              с именами согласно naming convection
     * @return объект класса clazz
     * */
    public T mapResultSetToClass(ResultSet resultSet, Class<T> clazz) throws
            NoSuchMethodException, InvocationTargetException,InstantiationException, IllegalAccessException, SQLException {

        Constructor<T> constructor = clazz.getConstructor();
        T obj = constructor.newInstance();
        int countUsefulFields = 0;

        for (int i = 0; i < clazz.getDeclaredFields().length; ++i){
            Field field = clazz.getDeclaredFields()[i];
            if (field.isAnnotationPresent(NotTableColumn.class)){
                continue;
            }
            ++countUsefulFields;
            Method setter = setterFromField(field);
            setter.invoke(obj, resultSet.getObject(countUsefulFields)); //у ResultSet номера полей с единицы
        }

        return obj;

    }

    public String generateSqlQueryForAdding(String nameTable, T obj) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder result = new StringBuilder(String.format("insert into %s values (", nameTable));
        for (Field field : obj.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(NotTableColumn.class)){
                continue;
            }
            Method getter = getterFromField(field);
            result.append(getter.invoke(obj));
            result.append(", ");
        }
        result.replace(result.length() - 1, result.length(), " );");

        return String.valueOf(result);
    }

    private static Method setterFromField(Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        //set + первая буква с заглавной + оставшиеся буквы
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return field.getDeclaringClass().getDeclaredMethod(setterName, field.getType());
    }
    private static Method getterFromField(Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        //get + первая буква с заглавной + оставшиеся буквы
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return field.getDeclaringClass().getDeclaredMethod(getterName);
    }
}
