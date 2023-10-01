package com.example.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Класс для объединения данных для GET запроса.
 * Он является ключом HashMap (WrapperMapCityWeather),
 * по которому получаем ответ на запрос
 * */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CoordinatesWeatherRequest {
    private String nameRegion;
    private LocalDate creationDate;
}
