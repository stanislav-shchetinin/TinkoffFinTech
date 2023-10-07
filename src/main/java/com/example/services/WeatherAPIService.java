package com.example.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherAPIService {
    private final WebClient webClient;

    @Value("${weatherapi.key}")
    private String key;
    public WeatherAPIService(@Qualifier("weatherAPIClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<Object> getWeather(String city){
        return  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", key)
                        .queryParam("q", city)
                        .queryParam("aqi", "no")
                        .build())
                .exchangeToMono(clientResponse -> {
                            return clientResponse.bodyToMono(Object.class);
                        }

                );
    }

}
