package com.example.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для обозначения идентификатора в таблице.
 * Если навешана на несколько полей класса, то идентификатором считается первое.
 * При вставке в таблицу происходит проверка поля - является ли идентификатором.
 * Если нет, то его значение не нужно, т.к. СУБД сама сгенерирует идентификатор
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
}
