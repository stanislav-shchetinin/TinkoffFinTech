package com.example.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Класс для сопоставления записям БД и Java сущностям
 * */
public class JdbcORM<T> {
    /**
     * @param resultSet запись таблицы
     * @param clazz класс, объект которого требуется получить из записи resultSet
     *              у класса должен быть пустой public конструктор и public сеттеры,
     *              с именами согласно naming convection
     * @return объект класса clazz
     * */
    public T mapResultSetToClass(ResultSet resultSet, Class<T> clazz) throws NoSuchMethodException,
            InvocationTargetException,InstantiationException, IllegalAccessException, SQLException {

        Constructor<T> constructor = clazz.getConstructor();
        T obj = constructor.newInstance();

        for (int i = 0; i < clazz.getDeclaredFields().length; ++i){
            Field field = clazz.getDeclaredFields()[i];
            Method setter = setterFromField(field);
            setter.invoke(obj, resultSet.getObject(i + 1)); //у ResultSet номера полей с единицы
        }

        return obj;

    }

    private static Method setterFromField(Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        //set + первая буква с заглавной + оставшиеся буквы
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return field.getDeclaringClass().getDeclaredMethod(setterName, field.getType());
    }
}
