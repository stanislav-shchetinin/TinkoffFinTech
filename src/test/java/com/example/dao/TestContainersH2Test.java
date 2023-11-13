package com.example.dao;

import com.example.WeatherApplication;
import com.example.entities.WeatherEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {WeatherApplication.class, JdbcTestConfiguration.class})
@Testcontainers
public class TestContainersH2Test {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WeatherServiceHibernate weatherServiceHibernate;

    @MockBean
    private WeatherRepoHibernate weatherRepoHibernate;

    @Container
    public static GenericContainer h2 = new GenericContainer(DockerImageName.parse("oscarfonts/h2"))
            .withExposedPorts(1521, 81)
            .withEnv("H2_OPTIONS", "-ifNotExists")
            .waitingFor(Wait.defaultWaitStrategy());

    @Test
    public void testWeatherService() {
        int numberOfRecordsBefore = ((List<WeatherEntity>)weatherRepoHibernate.findAll()).size();
        WeatherEntity weatherEntity = new WeatherEntity(1, 10., Date.valueOf("2022-01-01"));
        weatherServiceHibernate.save(weatherEntity);
        when(weatherRepoHibernate.findById(anyInt())).thenReturn(Optional.of(weatherEntity));

        Optional<WeatherEntity> weatherEntityTest = weatherServiceHibernate.get(1);
        weatherEntityTest.ifPresent(entity -> assertEquals(weatherEntity, entity));
        assertEquals(numberOfRecordsBefore + 1, weatherServiceHibernate.numberOfRecords());

    }

}