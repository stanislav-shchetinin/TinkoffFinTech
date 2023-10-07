package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfiguration {
    private static final String BASE_URL = "https://api.weatherapi.com/v1";
    @Bean
    public WebClient webClient(){
        return WebClient.create(BASE_URL);
    }
}
