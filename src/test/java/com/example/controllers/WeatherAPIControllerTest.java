package com.example.controllers;

import com.example.WeatherApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WeatherApplication.class
)
@AutoConfigureMockMvc
public class WeatherAPIControllerTest {

    private static final String NAME_CITY_FIRST = "Volgograd";
    private static final String NAME_CITY_SECOND = "Saint Petersburg";
    private static final String PARAMETER_NAME = "city";
    private static final String RESPONSE_TEMPERATURE = "temp_c";
    private static final String UNCORRECTED_CITY_ERROR = "No matching location found";
    private static final String URI = "/v1/current.json";
    private static final String UNCORRECTED_PARAMETER_NAME = "q";
    private static final String UNCORRECTED_NAME_CITY = "V";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WeatherAPIController weatherAPIController;

    /**
     * Проверка на корректную загрузка контекста
     * */
    @Test
    public void contextLoads() {
        assertThat(weatherAPIController).isNotNull();
    }

    /**
     * Запрос: корректное имя параметра, корректное имя региона
     * Ожидаемый ответ: json, в котором описывается температура в этом регионе
     * Ожидаемый код: 200 OK
     * */
    @Test
    public void correctCityInGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY_FIRST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME_CITY_FIRST)))
                .andExpect(content().string(containsString(RESPONSE_TEMPERATURE)));
    }

    /**
     * Запрос: корректное имя параметра, некорректное имя региона
     * Ожидаемый ответ: json с полем message = No matching location found
     * Ожидаемый код: 400 Bad Request
     * */
    @Test
    public void uncorrectedCityInGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, UNCORRECTED_NAME_CITY))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(UNCORRECTED_CITY_ERROR)));
    }

    /**
     * Запрос: без аргументов
     * Ожидаемый код: 400 Bad Request
     * */
    @Test
    public void nullCityInGetQuery() throws Exception {
        this.mockMvc.perform(get(URI))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    /**
     * Запрос: (1) корректное имя параметра, корректное имя региона;
     * (2) некорректное имя параметра, некорректное имя региона
     * Ожидаемый ответ: json, в котором описывается температура в регионе (1)
     * Ожидаемый код: 200 OK
     * */
    @Test
    public void correctCityAndUnknownParameterInGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY_FIRST)
                        .param(UNCORRECTED_PARAMETER_NAME, UNCORRECTED_NAME_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME_CITY_FIRST)))
                .andExpect(content().string(containsString(RESPONSE_TEMPERATURE)));
    }

    /**
     * Запрос: (1) некорректное имя параметра, некорректное имя региона
     * (2) корректное имя параметра, корректное имя региона;
     * Ожидаемый ответ: json, в котором описывается температура в регионе (2)
     * Ожидаемый код: 200 OK
     * */
    @Test
    public void unknownParameterAndCorrectCityInGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(UNCORRECTED_PARAMETER_NAME, UNCORRECTED_NAME_CITY)
                        .param(PARAMETER_NAME, NAME_CITY_FIRST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME_CITY_FIRST)))
                .andExpect(content().string(containsString(RESPONSE_TEMPERATURE)));
    }
    /**
     * Запрос: (1) корректное имя параметра, корректное имя региона
     * (2) корректное имя параметра, корректное имя региона;
     * Ожидаемый ответ: json, в котором описывается температура в регионе (1)
     * Ожидаемый код: 200 OK
     * */
    @Test
    public void twoCorrectedCityInGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY_SECOND)
                        .param(PARAMETER_NAME, NAME_CITY_FIRST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME_CITY_SECOND)))
                .andExpect(content().string(containsString(RESPONSE_TEMPERATURE)));
    }

    /**
     * Запрос: (1) корректное имя параметра, корректное имя региона
     * (2) корректное имя параметра, некорректное имя региона;
     * Ожидаемый ответ: json, в котором описывается температура в регионе (1)
     * Ожидаемый код: 200 OK
     * */
    @Test
    public void correctedAndUncorrectedCitiesGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY_SECOND)
                        .param(PARAMETER_NAME, UNCORRECTED_NAME_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME_CITY_SECOND)))
                .andExpect(content().string(containsString(RESPONSE_TEMPERATURE)));
    }

    /**
     * Запрос: (1) корректное имя параметра, некорректное имя региона
     * (2) корректное имя параметра, корректное имя региона;
     * Ожидаемый ответ: json, в котором описывается температура в регионе (2)
     * Ожидаемый код: 200 OK
     * */
    @Test
    public void uncorrectedAndCorrectedCitiesGetQuery() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, UNCORRECTED_NAME_CITY)
                        .param(PARAMETER_NAME, NAME_CITY_SECOND))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME_CITY_SECOND)))
                .andExpect(content().string(containsString(RESPONSE_TEMPERATURE)));
    }
}
