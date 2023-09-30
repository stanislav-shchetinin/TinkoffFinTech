package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import weather.FactoryWeather;
import weather.Weather;
import wrapper.WrapperListWeather;
import wrapper.WrapperMapCityWeather;
import wrapper.WrapperSetDelete;

@Configuration
public class ApplicationContextConfiguration {
    @Bean
    public WrapperListWeather listWeather(){
        return new WrapperListWeather();
    }
    @Bean
    public WrapperMapCityWeather mapCityWeather(){
        return new WrapperMapCityWeather();
    }
    @Bean
    public WrapperSetDelete setDelete(){
        return new WrapperSetDelete();
    }
    @Bean
    public FactoryWeather factoryWeather(){
        return new FactoryWeather();
    }
}
