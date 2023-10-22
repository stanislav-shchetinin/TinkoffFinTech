package com.example.services;

import com.example.exceptions.WeatherAPIClientException;
import com.example.exceptions.WeatherAPIServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class WeatherAPIService {
    private final WebClient webClient;

    @Value("${weatherapi.key}")
    private String key;

    private final String PARAMETER_KEY = "key";
    private final String PARAMETER_CITY_NAME = "q";
    private final String PARAMETER_NEED_INFO_AIR = "aqi";
    private final String NAME_FIELD_JSON_ERROR = "error";
    private final String NAME_FIELD_JSON_CODE = "code";
    public WeatherAPIService(@Qualifier("weatherAPIClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Map<String, Object> getWeather(String city) throws JsonProcessingException {
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam(PARAMETER_KEY, key)
                        .queryParam(PARAMETER_CITY_NAME, city)
                        .queryParam(PARAMETER_NEED_INFO_AIR, "no")
                        .build())
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

            Map<String, Object> jsonObject = new ObjectMapper().readValue(response.block(), HashMap.class);
            if (jsonObject.containsKey(NAME_FIELD_JSON_ERROR)){
                Map<String, Object> innerError = (Map<String, Object>) jsonObject.get(NAME_FIELD_JSON_ERROR);
                int codeError = (int) innerError.get(NAME_FIELD_JSON_CODE);

                if (codeError >= 2006 || codeError == 1002) {
                    throw new WeatherAPIServerException("Что-то пошло не так...");
                } else {
                    throw new WeatherAPIClientException((String) innerError.get("message"));
                }

            }

            return jsonObject;


    }

}
