package com.example.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Вспомогательная аннотация для JdbcORM
 * Показывает есть ли отображение поля Java класса и колонки таблицы БД
 * */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotTableColumn {

}
