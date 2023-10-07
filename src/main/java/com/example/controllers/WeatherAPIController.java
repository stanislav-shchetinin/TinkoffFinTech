package com.example.controllers;

import com.example.services.WeatherAPIService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name="WeatherAPIController",
        description="Контроллер для получения погоды из сервиса WeatherAPI")
public class WeatherAPIController {

    private final WeatherAPIService weatherAPIService;
    @GetMapping("/current.json")
    public Map<String, Object> getWeather(@RequestParam String city){
        return  weatherAPIService.getWeather(city);
    }

}
