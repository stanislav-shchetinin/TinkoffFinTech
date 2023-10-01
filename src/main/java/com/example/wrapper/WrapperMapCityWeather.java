package com.example.wrapper;

import com.example.requests.CoordinatesWeatherRequest;
import com.example.weather.Weather;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Класс-обертка для HashMap, из которого за O(1) получаем ответ на GET запрос
 * */
@Component
public class WrapperMapCityWeather {
    @Getter
    private final HashMap<CoordinatesWeatherRequest, Double> hashMap;
    public WrapperMapCityWeather(){
        hashMap = new HashMap<>();
    }
    public void add(Weather weather){
        hashMap.put( new CoordinatesWeatherRequest(
                weather.getNameRegion(), weather.getCreationDate().toLocalDate()),
                weather.getTemperature());
    }
    public Double get(String nameRegion, LocalDate localDate){
        return hashMap.get(new CoordinatesWeatherRequest(nameRegion, localDate));
    }
    public boolean isInMap(String nameRegion, LocalDate localDate){
        return hashMap.containsKey(new CoordinatesWeatherRequest(nameRegion, localDate));
    }
}
