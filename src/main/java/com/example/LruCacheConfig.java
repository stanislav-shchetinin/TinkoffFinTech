package com.example;

import com.example.cache.LruCache;
import com.example.weather.Weather;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class LruCacheConfig {
    @Bean
    public LruCache<String, Weather> h2DataSource() {
        return new LruCache<>();
    }
}
