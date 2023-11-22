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
import org.mockito.Spy;
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
        classes = WeatherApplication.class
)
public class WeatherCityServiceCacheTest {

    @Autowired
    private  WeatherCityService weatherCityService;
    @Autowired
    private WrapperMapCityWeather mapCityWeather;
    @Autowired
    private LruCache<String, Weather> lruCache;
    @Autowired
    private FactoryWeather factoryWeather;

    private static final String NAME_CITY = "Volgograd";
    private static final LocalDateTime DATA = LocalDateTime.parse("2007-12-03T10:15:30");
    @Test
    public void contextLoads() {
        assertThat(weatherCityService).isNotNull();
    }

    @Test
    public void addedInCache() {
        weatherCityService.getTemperature(NAME_CITY, LocalDate.now());
        assertThat(lruCache.get(NAME_CITY)).isNotNull();
    }

    @Test
    public void noAccessBase() {
        Double tempBase = 1000.;
        Double tempCache = 10.;

        Weather weatherBase = factoryWeather.createWeather(NAME_CITY, tempBase, DATA);
        mapCityWeather.add(weatherBase);

        Weather weather = factoryWeather.createWeather(NAME_CITY, tempCache, DATA);
        lruCache.add(NAME_CITY, weather);
        Double temperature = weatherCityService.getTemperature(NAME_CITY, DATA.toLocalDate());
        assert(temperature.equals(tempCache));
    }

    @Test
    public void deleteFromCache() {
        Double temp = 10.;
        Weather weather = factoryWeather.createWeather(NAME_CITY, temp, DATA);
        weatherCityService.add(weather.getNameRegion(), new WeatherLiteRequest(
                weather.getTemperature(), weather.getCreationDate()
        ));
        weatherCityService.getTemperature(weather.getNameRegion(), weather.getCreationDate().toLocalDate());
        weatherCityService.deleteRegion(weather.getNameRegion());
        assertThat(lruCache.get(weather.getNameRegion())).isNull();
    }
}
