package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cache.course")
@Setter
@Getter
public class CacheConfig {
    private int size;
    private float loadFactor;
    private boolean accessOrder;
    @Bean
    @Qualifier("cachedMap")
    public Map cachedMap() {
        return new LinkedHashMap<>(size, loadFactor, accessOrder){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > size;
            }
        };
    }
}