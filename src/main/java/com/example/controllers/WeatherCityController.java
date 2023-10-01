package com.example.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.requests.WeatherLiteRequest;
import com.example.weather.FactoryWeather;
import com.example.wrapper.MainWrapper;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather/{city}")
@AllArgsConstructor
public class WeatherCityController {

    private final MainWrapper mainWrapper;
    private final FactoryWeather factoryWeather;

    /**
     * В качестве аргумента метод получает доту (без времени)
     * И выдает по ней температуру.
     * Если на заданую дату было несколько Weather с разным временем, то берется
     * последнее добавленное
     * */
    @GetMapping
    public Double getTemperatureByDate(@PathVariable String city,
                                       @RequestParam String date){
        LocalDate localDate = LocalDate.parse(date);
        if (!mainWrapper.getSetDelete().isInSet(city) &&
                mainWrapper.getMapCityWeather().isInMap(city, localDate)){
            return mainWrapper.getMapCityWeather().get(city, localDate);
        }
        return null;
    }
    @PostMapping
    public void postWeather(@PathVariable String city,
                            @RequestBody WeatherLiteRequest weatherLite){
        mainWrapper.getSetDelete().removeRegion(city);
        mainWrapper.add(
                factoryWeather.createWeather(city, weatherLite.getTemperature(), weatherLite.getCreationDate())
        );
    }
    @PutMapping
    public void putWeather(@PathVariable String city,
                            @RequestBody WeatherLiteRequest weatherLite){
        mainWrapper.getSetDelete().removeRegion(city);
        mainWrapper.update(
                factoryWeather.createWeather(city, weatherLite.getTemperature(), weatherLite.getCreationDate())
        );
    }
    /**
     * Удаление не происходит напрямую. Удаленный элемент помещается в set (с этого для пользователя он удален)
     * Если элемент нужно вернуть из этого сета он возвращается (отсюда некоторые проблемы с неймингом методов)
     * Удаление за O(1)
     * */
    @DeleteMapping
    public void deleteWeather(@PathVariable String city){
        mainWrapper.getSetDelete().addRegion(city);
    }

}
