package com.example.controllers;

import com.example.services.WeatherAPIService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name="WeatherAPIController",
        description="Контроллер для получения погоды из сервиса WeatherAPI")
public class WeatherAPIController {

    private final WeatherAPIService weatherAPIService;
    @GetMapping("/current.json")
    @RateLimiter(name="weatherAPIService")
    public Map<String, Object> getWeather(@RequestParam String city){
        return  weatherAPIService.getWeather(city);
    }

}
