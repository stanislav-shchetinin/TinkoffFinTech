package com.example.controllers;

import com.example.WeatherApplication;
import com.example.configs.SecurityConfiguration;
import com.example.services.WeatherCityBehavior;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {WeatherApplication.class, SecurityConfiguration.class}
)
@AutoConfigureMockMvc
public class WeatherCityControllerSecurityTest {
    private static final String URI = "/api/weather/{city}";
    private static final String NAME_CITY = "Volgograd";
    private static final String PARAMETER_NAME = "localDate";
    private static final String LOCAL_DATE = "2022-01-01";
    private static final LocalDate DATE = LocalDate.parse("2022-01-01");
    private static final Double TEMPERATURE = 10.;

    private static final String JSON = "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}";

    @InjectMocks
    private WeatherCityController weatherCityController;
    @MockBean
    private WeatherCityBehavior weatherCityServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getNotAuth() throws Exception {
        this.mockMvc.perform(get(URI, NAME_CITY)
                        .param(PARAMETER_NAME, LOCAL_DATE))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    public void getUser() throws Exception {
        when(weatherCityServiceMock.getTemperature(NAME_CITY, DATE))
                .thenReturn(TEMPERATURE);
        when(weatherCityServiceMock.isEntryInBase(NAME_CITY, DATE))
                .thenReturn(true);
        this.mockMvc.perform(get(URI, NAME_CITY)
                        .param(PARAMETER_NAME, LOCAL_DATE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getAdmin() throws Exception {
        when(weatherCityServiceMock.getTemperature(NAME_CITY, DATE))
                .thenReturn(TEMPERATURE);
        when(weatherCityServiceMock.isEntryInBase(NAME_CITY, DATE))
                .thenReturn(true);
        this.mockMvc.perform(get(URI, NAME_CITY)
                        .param(PARAMETER_NAME, LOCAL_DATE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postNotAuth() throws Exception {
        this.mockMvc.perform(post(URI, NAME_CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    public void postUser() throws Exception {
        this.mockMvc.perform(post(URI, NAME_CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void postAdmin() throws Exception {
        this.mockMvc.perform(post(URI, NAME_CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void putNotAuth() throws Exception {
        this.mockMvc.perform(post(URI, NAME_CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    public void putUser() throws Exception {
        this.mockMvc.perform(post(URI, NAME_CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void putAdmin() throws Exception {
        this.mockMvc.perform(post(URI, NAME_CITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"temperature\": 10, \"creationDate\": \"2019-11-15T13:15:30\"}"
                        ))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void deleteNotAuth() throws Exception {
        this.mockMvc.perform(delete(URI, NAME_CITY))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = {"USER"})
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete(URI, NAME_CITY))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deleteAdmin() throws Exception {
        this.mockMvc.perform(delete(URI, NAME_CITY))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
