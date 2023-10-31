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
    private final int KEY_NOT_PROVIDED = 1002;
    private final int KEY_API_ERROR_FIRST_DIGIT = 2;
    private final int SERVER_ERROR_FIRST_DIGIT = 9;
    private final String UNIDENTIFIED_ERROR = "Что-то пошло не так...";
    private final String ERROR_MESSAGE_IN_JSON = "message";
    public WeatherAPIService(@Qualifier("weatherAPIClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Map<String, Object> getWeather(String city){
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam(PARAMETER_KEY, key)
                        .queryParam(PARAMETER_CITY_NAME, city)
                        .queryParam(PARAMETER_NEED_INFO_AIR, "no")
                        .build())
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

        JSONObject jsonObject = new JSONObject(response.block());

        if (jsonObject.has(NAME_FIELD_JSON_ERROR)){
            JSONObject innerError = jsonObject.getJSONObject(NAME_FIELD_JSON_ERROR);
            int codeError = innerError.getInt(NAME_FIELD_JSON_CODE);
            int firstDigitCodeError = codeError / 1000;
            if (firstDigitCodeError == KEY_API_ERROR_FIRST_DIGIT
                    || firstDigitCodeError == SERVER_ERROR_FIRST_DIGIT
                    || codeError == KEY_NOT_PROVIDED) {
                throw new WeatherAPIServerException(UNIDENTIFIED_ERROR);
            } else {
                throw new WeatherAPIClientException(innerError.getString(ERROR_MESSAGE_IN_JSON));
            }

        }

        return jsonObject.toMap();
    }

}
