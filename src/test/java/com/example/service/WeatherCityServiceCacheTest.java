package com.example.service;

import com.example.WeatherApplication;
import com.example.cache.LruCache;
import com.example.requests.WeatherLiteRequest;
import com.example.services.WeatherCityService;
import com.example.weather.FactoryWeather;
import com.example.weather.Weather;
import com.example.wrapper.WrapperMapCityWeather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WeatherApplication.class
)
@AutoConfigureMockMvc
public class WeatherCityServiceCacheTest {

    @InjectMocks
    private  WeatherCityService weatherCityService;
    @Mock
    private WrapperMapCityWeather mapCityWeather;
    @Mock
    private LruCache<String, Weather> lruCache;

    @Autowired
    private FactoryWeather factoryWeather;
    @Test
    public void contextLoads() {
        assertThat(weatherCityService).isNotNull();
    }

    @Test
    public void addedInCache() {
        String nameRegion = "Volgograd";
        weatherCityService.getTemperature(nameRegion, LocalDate.now());
        assertThat(lruCache.get(nameRegion)).isNotNull();
    }

    @Test
    public void noAccessBase() {
        boolean access = false;

        String nameRegion = "Volgograd";
        LocalDateTime localDateTime = LocalDateTime.parse("2007-12-03T10:15:30");
        Double tempBase = 1000.;
        Double tempCache = 10.;

        when(mapCityWeather.get(nameRegion, localDateTime.toLocalDate()))
                .thenReturn(tempBase);


        Weather weather = factoryWeather.createWeather(nameRegion, tempCache, localDateTime);
        lruCache.add(nameRegion, weather);
        Double temperature = weatherCityService.getTemperature(nameRegion, localDateTime.toLocalDate());
        assert(temperature.equals(tempCache));
    }
}
