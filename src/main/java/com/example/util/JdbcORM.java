package com.example.util;

import com.example.entities.WeatherEntity;
import com.example.exceptions.EmptyClassException;
import com.example.util.annotations.ID;
import com.example.util.annotations.NameColumn;
import com.example.util.annotations.NotTableColumn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для сопоставления записям БД и Java сущностям
 * */
@Component
public class JdbcORM<T> {
    /**
     * Отображает ResultSet в объект класса
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

    /**
     * Генерерует запрос добавления в базу данных
     * @param nameTable имя таблицы куда должна быть произвидена вставка
     * @param obj объект, который отображается в эту таблицу
     * @return SQL запрос в виде строки
     * */
    public String generateSqlQueryForAdding(String nameTable, T obj) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        List<Field> fieldList = getFieldColumnFromEntity(obj);
        StringBuilder result = new StringBuilder(String.format("insert into %s (", nameTable));

        //Выстраиваем имена колонок, в которые будет перенесены значения
        //Пример: weather (city_id, temperature, date)
        for (Field field : fieldList){
            result.append(field.getDeclaredAnnotation(NameColumn.class).name());
            result.append(", ");
        }
        //Т.к. после каждого имени добавлялась запятая, то в конце она тоже стоит
        //Ее нужно заменить на скобку
        result.replace(result.length() - 2, result.length(), ") ");

        //Собиарем значения полей
        result.append("values (");
        for (Field field : fieldList){
            Method getter = getterFromField(field);
            result.append("'");
            result.append(getter.invoke(obj));
            result.append("', ");
        }
        result.replace(result.length() - 2, result.length(), ");");

        return String.valueOf(result);
    }

    public String generateSqlQueryForUpdating(String nameTable, T obj) throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        List<Field> fieldList = getFieldColumnFromEntity(obj);
        Field fieldId = fieldIdInEntity(obj);

        StringBuilder result = new StringBuilder(String.format("update %s set ", nameTable));

        for (Field field : fieldList){
            result.append(String.format("%s = '%s', ",
                    field.getDeclaredAnnotation(NameColumn.class).name(),
                    getterFromField(field).invoke(obj)));
        }

        result.replace(result.length() - 2, result.length(),
                String.format(" where %s = %s", fieldId.getName(), getterFromField(fieldId).invoke(obj)));

        return String.valueOf(result);

    }

    /**
     * Возвращает лист полей объекта, которые явлюятся колонками таблицы (не реализуют @NotTableColumn),
     * не являются @ID, есть имя колонки, в которую отображаются (@NameColumn), не null
     * */
    private List<Field> getFieldColumnFromEntity(T obj) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        List<Field> fieldList = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()){
            if (!field.isAnnotationPresent(NotTableColumn.class)
                    && !field.isAnnotationPresent(ID.class)
                    && field.isAnnotationPresent(NameColumn.class)
                    && getterFromField(field).invoke(obj) != null){
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    /**
     * Метод для поиска id
     * Если есть поля помеченные аннотацией @ID, то идентификатором считается первое из таких
     * Если такого поля нет, то идентификатором считается первое поле класса
     * Если у класса нет полей, то возвращается исключение
     * */
    private Field fieldIdInEntity(T obj) throws EmptyClassException{
        Field firstField = null;
        for (Field field : obj.getClass().getDeclaredFields()){
            if (firstField == null){
                firstField = field;
            }
            if (field.isAnnotationPresent(ID.class)){
                return field;
            }
        }
        if (firstField != null) {
            return firstField;
        } else {
            throw new EmptyClassException();
        }
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
