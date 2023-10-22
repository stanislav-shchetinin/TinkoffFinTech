package com.example.transaction;

import com.example.entities.CityEntity;
import com.example.services.WeatherAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAPITransactionService {

    private final WeatherAPIService weatherAPIService;
    private final CityRepo cityRepo;
    private final WeatherAPIRepo weatherAPIRepo;

    @Transactional
    public void saveInDataBaseWeatherInCity(String city) {
        try {
            Map<String, Object> obj = weatherAPIService.getWeather(city);
            Map<String, Object> current = (Map<String, Object>) obj.get("current");
            System.out.println(current);
            Double temperature = (Double) current.get("temp_c");
            Timestamp timestamp = Timestamp.valueOf(current.get("last_updated").toString() + ":00");

            Optional<CityEntity> cityEntity = cityRepo.getByName(city);
            if (cityEntity.isPresent()){
                WeatherAPIEntity weatherAPIEntity = new WeatherAPIEntity(
                        cityEntity.get(), temperature, timestamp
                );
                System.out.println(weatherAPIEntity);
                weatherAPIRepo.save(weatherAPIEntity);
            } else {
                log.warn("City not found");
            }

        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
        }

    }

}
