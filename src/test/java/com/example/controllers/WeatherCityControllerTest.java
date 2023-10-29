package com.example.controllers;

import com.example.WeatherApplication;
import com.example.services.WeatherCityBehavior;
import com.example.services.WeatherCityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @InjectMocks
    private WeatherCityController weatherCityController;
    @MockBean
    private WeatherCityBehavior weatherCityService;

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void contextLoads() {
        assertThat(weatherCityController).isNotNull();
    }

    @Test
    public void getTemperatureByDate_validRegionAndDate_returnTemperature() throws Exception{
        final LocalDate DATE = LocalDate.parse(DATE_STRING);
        final Double temperature = -10.;
        when(weatherCityService.getTemperature(NAME_REGION, DATE))
                .thenReturn(temperature);
        when(weatherCityService.isEntryInBase(NAME_REGION, DATE))
                .thenReturn(true);

        mockMvc.perform(get(URI, NAME_REGION)
                        .param(NAME_DATE_PARAMETER, DATE_STRING))
                .andExpect(status().isOk());
        verify(weatherCityService, times(1)).getTemperature(NAME_REGION, DATE);
    }

}
