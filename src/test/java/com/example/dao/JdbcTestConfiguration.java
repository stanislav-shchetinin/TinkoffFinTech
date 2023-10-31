package com.example.dao;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class JdbcTestConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");

        dataSource.setUrl("jdbc:h2:tcp://localhost:" + TestContainersH2Test.h2.getMappedPort(1521) + "/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return new JdbcTemplate(dataSource);
    }
}
