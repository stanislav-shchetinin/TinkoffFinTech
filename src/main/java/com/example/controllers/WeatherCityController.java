package com.example.controllers;

import com.example.requests.CoordinatesWeatherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.requests.WeatherLiteRequest;
import com.example.weather.FactoryWeather;
import com.example.wrapper.MainWrapper;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/weather/{city}")
public class WeatherCityController {

    private final MainWrapper mainWrapper;
    private final FactoryWeather factoryWeather;
    @Autowired
    public WeatherCityController(MainWrapper mainWrapper, FactoryWeather factoryWeather){
        this.mainWrapper = mainWrapper;
        this.factoryWeather = factoryWeather;
    }
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
    @DeleteMapping
    public void deleteWeather(@PathVariable String city){
        mainWrapper.getSetDelete().addRegion(city);
    }

}
