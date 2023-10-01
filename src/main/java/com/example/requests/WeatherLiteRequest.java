package com.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * Класс для объединения данных из тела POST/PUT запросов.
 * */
@Getter
@AllArgsConstructor
public class WeatherLiteRequest {
    private Double temperature;
    private LocalDateTime creationDate;
}
