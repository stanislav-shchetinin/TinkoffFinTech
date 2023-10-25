package com.example.controllers;

import com.example.services.WeatherAPIService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name="WeatherAPIController",
        description="Контроллер для получения погоды из сервиса WeatherAPI")
public class WeatherAPIController {

    private final WeatherAPIService weatherAPIService;

    @Operation(
            summary = "Получение погоды",
            description = "Позволяет получить текущую погоду по городу из сервиса WeatherAPI"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Погода получена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметр city не предоставлен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Недопустимый URL"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Местоположение параметра city не найдено"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка на сервере при работе с API"
            )
    })
    @GetMapping("/current.json")
    @RateLimiter(name="weatherAPIService")
    public Map<String, Object> getWeather(@RequestParam String city){
        return  weatherAPIService.getWeather(city);
    }

}
