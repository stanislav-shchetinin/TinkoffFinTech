package com.example.util.annotations;

/**
 * Вспомогательная аннотация для JdbcORM
 * Показывает что данное поле хранит информацию нужную только для реализации логики
 * */
@NotTableColumn
public @interface SupportingInfo {
}
