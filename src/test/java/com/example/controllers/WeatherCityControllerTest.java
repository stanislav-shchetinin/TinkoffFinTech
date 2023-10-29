package com.example.controllers;

import com.example.WeatherApplication;
import com.example.services.WeatherCityBehavior;
import com.example.services.WeatherCityStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WeatherApplication.class
)
@AutoConfigureMockMvc
public class WeatherCityControllerTest {
    private static final String URI = "/api/weather/{city}";
    private static final String NAME_REGION = "Volgograd";
    private static final String DATE_STRING = "2022-01-01";
    private static final String NAME_DATE_PARAMETER = "localDate";
    private static final Double TEMPERATURE = -10.;
    private final LocalDate DATE = LocalDate.parse(DATE_STRING);
    @InjectMocks
    private WeatherCityController weatherCityController;
    @MockBean
    private WeatherCityBehavior weatherCityServiceMock;
    @SpyBean
    private WeatherCityBehavior weatherCityServiceSpy;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(weatherCityController).isNotNull();
    }

    /**
     * Запрос: корректное имя региона, корректная дата
     * Ожидаемый ответ: json с температурой
     * Ожидаемый код: 200 OK
     * (Stub)
     * */
    @Test
    public void getTemperatureByDate_validRegionAndDate_returnTemperature() throws Exception {
        WeatherCityBehavior weatherCityServiceStub = new WeatherCityStub();
        MockMvc mockMvcWithStub = MockMvcBuilders.standaloneSetup(
                new WeatherCityController(weatherCityServiceStub)
        ).build();
        mockMvcWithStub.perform(get(URI, NAME_REGION)
                        .param(NAME_DATE_PARAMETER, DATE_STRING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.temperature").value(TEMPERATURE));
    }

    /**
     * Запрос: корректное имя региона, корректная дата, но города нет в базе (был удален)
     * Ожидаемый ответ: json с ошибкой
     * Ожидаемый код: 404 NotFound
     * (Mock)
     * */
    @Test
    public void getTemperatureByDate_noEntryInBase_notFound() throws Exception{
        when(weatherCityServiceMock.getTemperature(NAME_REGION, DATE))
                .thenReturn(TEMPERATURE);
        when(weatherCityServiceMock.isEntryInBase(NAME_REGION, DATE))
                .thenReturn(false);

        mockMvc.perform(get(URI, NAME_REGION)
                        .param(NAME_DATE_PARAMETER, DATE_STRING))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(weatherCityServiceMock, times(0)).getTemperature(NAME_REGION, DATE);
    }

    /**
     * Запрос: post запрос на добавление погоды в базу
     * Ожидаемый код: 200 OK
     * (Spy)
     * */
    @Test
    public void postWeather_saveInBase() throws Exception {
        mockMvc.perform(post(URI, NAME_REGION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andExpect(status().isOk());
        verify(weatherCityServiceSpy, times(1)).add(eq(NAME_REGION), any());
    }

}
