package com.example.controllers;

import com.example.WeatherApplication;
import com.example.services.WeatherCityBehavior;
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

import java.time.LocalDate;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
     * */
    @Test
    public void getTemperatureByDate_validRegionAndDate_returnTemperature() throws Exception{
        final Double temperature = -10.;
        when(weatherCityServiceMock.getTemperature(NAME_REGION, DATE))
                .thenReturn(temperature);
        when(weatherCityServiceMock.isEntryInBase(NAME_REGION, DATE))
                .thenReturn(true);

        mockMvc.perform(get(URI, NAME_REGION)
                        .param(NAME_DATE_PARAMETER, DATE_STRING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.temperature").value(temperature));
        verify(weatherCityServiceMock, times(1)).getTemperature(NAME_REGION, DATE);
    }

    @Test
    public void getTemperatureByDate_invalidRegion_notFound() throws Exception{
        final Double temperature = -10.;
        when(weatherCityServiceSpy.getTemperature(NAME_REGION, DATE))
                .thenReturn(temperature);
        when(weatherCityServiceSpy.isEntryInBase(NAME_REGION, DATE))
                .thenReturn(false);

        mockMvc.perform(get(URI, NAME_REGION)
                        .param(NAME_DATE_PARAMETER, DATE_STRING))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
