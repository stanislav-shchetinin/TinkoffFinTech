package com.example.dao;

import com.example.WeatherApplication;
import com.example.entities.WeatherEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {WeatherApplication.class, JdbcTestConfiguration.class})
@Testcontainers
public class TestContainersH2Test {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WeatherServiceHibernate weatherServiceHibernate;

    @MockBean
    private WeatherRepoHibernate weatherRepoHibernate;

    @Container
    public static GenericContainer h2 = new GenericContainer(DockerImageName.parse("oscarfonts/h2"))
            .withExposedPorts(1521, 81)
            .withEnv("H2_OPTIONS", "-ifNotExists")
            .waitingFor(Wait.defaultWaitStrategy());

    @Test
    public void testSelectFromTestTable() {

        jdbcTemplate.execute(
                "CREATE TABLE tinkoff (" +
                        "    ID INT PRIMARY KEY," +
                        "    VAL VARCHAR(255)" +
                        ")");
        jdbcTemplate.update(
                "INSERT INTO tinkoff (ID, VAL) VALUES (?, ?)",
                1, "Test Val");
        String testName = jdbcTemplate.queryForObject("SELECT VAL FROM tinkoff WHERE id = 1", String.class);

        assertThat(testName).isEqualTo("Test Val");
    }

    @Test
    public void testWeatherService() {
        int numberOfRecordsBefore = ((List<WeatherEntity>)weatherRepoHibernate.findAll()).size();
        WeatherEntity weatherEntity = new WeatherEntity(1, 10., Date.valueOf("2022-01-01"));
        weatherRepoHibernate.save(weatherEntity);
        when(weatherRepoHibernate.findById(1)).thenReturn(Optional.of(weatherEntity));

        Optional<WeatherEntity> weatherEntityTest = weatherServiceHibernate.get(1);
        weatherEntityTest.ifPresent(entity -> assertEquals(weatherEntity, entity));
        assertEquals(numberOfRecordsBefore + 1, weatherServiceHibernate.numberOfRecords());

    }

}