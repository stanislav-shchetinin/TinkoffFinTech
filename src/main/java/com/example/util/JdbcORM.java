package com.example.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcORM<T> {
    public T mapResultSetToClass(ResultSet resultSet, Class<T> clazz) throws NoSuchMethodException,
            InvocationTargetException,InstantiationException, IllegalAccessException, SQLException {

        Constructor<T> constructor = clazz.getConstructor();
        T obj = constructor.newInstance();

        for (int i = 0; i < clazz.getDeclaredFields().length; ++i){
            Field field = clazz.getDeclaredFields()[i];
            Method getter = setterFromField(field);
            getter.invoke(obj, resultSet.getObject(i + 1));
        }

        return obj;

    }

    private static Method setterFromField(Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return field.getDeclaringClass().getDeclaredMethod(getterName);
    }
}
