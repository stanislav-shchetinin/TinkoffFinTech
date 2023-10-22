package com.example.transaction;

import com.example.services.WeatherAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAPITransactionService {
    private final WeatherAPIService weatherAPIService;

    @Transactional
    public void saveInDataBaseWeatherInCity(String city) {
        try {
            Map<String, Object> obj = weatherAPIService.getWeather(city);
            Map<String, Object> current = (Map<String, Object>) obj.get("current");
            System.out.println(current);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
