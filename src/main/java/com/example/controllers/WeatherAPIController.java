package com.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name="WeatherAPIController",
        description="Контроллер для получения погоды из сервиса WeatherAPI")
public class WeatherAPIController {

    private final WebClient webClient;

    @Value("${weatherapi.key}")
    private String key;
    @GetMapping("/current.json")
    public Mono<Object> getWeather(@RequestParam String city){
        return  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", key)
                        .queryParam("q", city)
                        .queryParam("aqi", "no")
                        .build())
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Object.class));
    }

}
