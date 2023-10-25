package com.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfiguration {
    @Value("${weatherapi.baseUrl}")
    private String BASE_URL;
    @Bean
    @Qualifier("weatherAPIClient")
    public WebClient webClient(){

        return WebClient.create(BASE_URL);
    }

}
