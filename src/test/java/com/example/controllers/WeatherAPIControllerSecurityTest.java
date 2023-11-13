package com.example.controllers;

import com.example.WeatherApplication;
import com.example.configs.SecurityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {WeatherApplication.class, SecurityConfiguration.class}
)
@AutoConfigureMockMvc
public class WeatherAPIControllerSecurityTest {

    private static final String URI = "/v1/current.json";
    private static final String PARAMETER_NAME = "city";
    private static final String NAME_CITY = "Volgograd";

    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(roles = {"USER"})
    public void permissionUser() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void permissionAdmin() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void permissionNotAuth() throws Exception {
        this.mockMvc.perform(get(URI)
                        .param(PARAMETER_NAME, NAME_CITY))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
