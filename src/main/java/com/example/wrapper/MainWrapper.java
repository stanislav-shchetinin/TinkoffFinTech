package com.example.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.example.weather.Weather;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Component
public class MainWrapper {
    private WrapperListWeather listWeather;
    private WrapperMapCityWeather mapCityWeather;
    private WrapperSetDelete setDelete;
    public void add(Weather weather){
        listWeather.add(weather);
        mapCityWeather.add(weather);
    }
    public void deleteRegion(String nameRegion){
        setDelete.addRegion(nameRegion);
    }
}
