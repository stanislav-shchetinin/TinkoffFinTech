package com.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Класс для объединения данных из тела POST/PUT запросов.
 * */
@Getter
@AllArgsConstructor
public class WeatherLiteRequest {
    private Double temperature;
    private LocalDateTime creationDate;
}
