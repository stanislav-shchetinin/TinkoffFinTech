package controllers;

import com.example.ApplicationContextConfiguration;
import com.example.WeatherApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import requests.CoordinatesWeatherRequest;
import wrapper.MainWrapper;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/weather/{city}")
public class WeatherCityController {

    private final MainWrapper mainWrapper;

    public WeatherCityController(){
        this.mainWrapper = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class)
                .getBean(MainWrapper.class);
    }
    @GetMapping
    public Double getTemperatureByDate(@PathVariable String city,
                                       @RequestParam ZonedDateTime dateTime){
        return mainWrapper.getMapCityWeather().get(city, dateTime);
    }
}
