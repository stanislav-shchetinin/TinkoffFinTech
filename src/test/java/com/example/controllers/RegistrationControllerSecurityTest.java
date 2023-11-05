package com.example.controllers;

import com.example.WeatherApplication;
import com.example.configs.SecurityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {WeatherApplication.class, SecurityConfiguration.class}
)
@AutoConfigureMockMvc
public class RegistrationControllerSecurityTest {

    private static final String URI = "/registration";
    private static final String JSON = "{\"username\": \"%s\", \"password\": \"%s\"}";

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void registrationNewUser() throws Exception {
        this.mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        String.format(JSON, "stass", "12345")
                ))
                .andExpect(status().isOk());
    }
    @Test
    public void registrationExistsUser() throws Exception {
        this.mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                String.format(JSON, "admin", "1xxx")
                        ))
                .andExpect(status().isConflict());
    }
}
