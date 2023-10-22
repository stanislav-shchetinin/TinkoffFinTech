package com.example.transaction;

import com.example.entities.CityEntity;
import com.example.exceptions.NotFoundException;
import com.example.services.WeatherAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAPITransactionService {

    private final WeatherAPIService weatherAPIService;
    private final CityRepo cityRepo;
    private final WeatherAPIRepo weatherAPIRepo;
    private final TransactionTemplate transactionTemplate;

    /**
     * Декларативное создание транзакции
     * Уровень изолялии - READ_UNCOMMITTED, т.к. внутри транзакции не происходит
     * считывание данных, только запись
     * */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void saveEntity(String city) {
        log.info("Transaction begin");
        try {
            Map<String, Object> obj = weatherAPIService.getWeather(city);
            WeatherAPIEntity weatherAPIEntity = buildWeatherAPIEntity(city, obj);
            weatherAPIRepo.save(weatherAPIEntity);
        } catch (JsonProcessingException | NotFoundException e) {
            log.warn(e.getMessage(), e);
        }
        log.info("Transaction end");
    }

    /**
     * Императивное создание транзакции
     * Уровень изоляции - SERIALIZABLE
     * Нужно заблокировать добавление новых записей
     * Проверяет есть ли запись с таким городом и временем в базе, если нет, то добавляет
     * */
    public Boolean saveAndCheck(String city){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        return transactionTemplate.execute(status -> {
            try {
                Map<String, Object> obj = weatherAPIService.getWeather(city);
                WeatherAPIEntity weatherAPIEntity = buildWeatherAPIEntity(city, obj);
                WeatherAPIEntity weatherAPIEntityInTable = weatherAPIRepo.findByCityAndTimestamp(
                        weatherAPIEntity.getCity(), weatherAPIEntity.getTimestamp()
                );
                if (weatherAPIEntityInTable == null){
                    weatherAPIRepo.save(weatherAPIEntity);
                    return true;
                } else {
                    return false;
                }
            } catch (JsonProcessingException | NotFoundException e) {
                log.warn(e.getMessage(), e);
                return false;
            }
        });
    }

    private WeatherAPIEntity buildWeatherAPIEntity(String city, Map<String, Object> obj){
        Map<String, Object> current = (Map<String, Object>) obj.get("current");
        Double temperature = (Double) current.get("temp_c");
        Timestamp timestamp = Timestamp.valueOf(current.get("last_updated").toString() + ":00");

        Optional<CityEntity> cityEntity = cityRepo.getByName(city);
        if (cityEntity.isPresent()){
            return new WeatherAPIEntity(
                    cityEntity.get(), temperature, timestamp
            );
        } else {
            throw new NotFoundException("City not found");
        }
    }

}
