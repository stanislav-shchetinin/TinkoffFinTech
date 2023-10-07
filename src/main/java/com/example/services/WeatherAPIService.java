package com.example.services;

import com.example.exceptions.WeatherAPIClientException;
import com.example.exceptions.WeatherAPIServerException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.Map;

@Service
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

            if (codeError >= 2006 || codeError == 1002) {
                throw new WeatherAPIServerException("Что-то пошло не так...");
            } else {
                throw new WeatherAPIClientException(innerError.getString("message"));
            }

        }

        return jsonObject.toMap();
    }

}
