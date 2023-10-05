package com.example.services;

import com.example.requests.WeatherLiteRequest;
import com.example.weather.FactoryWeather;
import com.example.wrapper.WrapperListWeather;
import com.example.wrapper.WrapperMapCityWeather;
import com.example.wrapper.WrapperSetDelete;
import lombok.Getter;
import com.example.weather.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Service
public class WeatherCityService {
    private final WrapperListWeather listWeather;
    private final WrapperMapCityWeather mapCityWeather;
    private final WrapperSetDelete setDelete;
    private final FactoryWeather factoryWeather;
    public void add(String nameRegion, WeatherLiteRequest weatherLite){
        Weather weather = factoryWeather.createWeather(
                nameRegion, weatherLite.getTemperature(), weatherLite.getCreationDate()
        );
        setDelete.removeRegion(nameRegion);
        listWeather.add(weather);
        mapCityWeather.add(weather);
    }

    public boolean isRegionInBase(String nameRegion){
        return !setDelete.isInSet(nameRegion);
    }

    public boolean isEntryInBase(String nameRegion, LocalDate localDate){
        return isRegionInBase(nameRegion) &&
                mapCityWeather.isInMap(nameRegion, localDate);
    }
    public boolean deleteRegion(String nameRegion){
        return setDelete.addRegion(nameRegion);
    }
    public Double getTemperature(String nameRegion, LocalDate localDate){
        return mapCityWeather.get(nameRegion, localDate);
    }
    public void update(String nameRegion, WeatherLiteRequest weatherLite){
        Weather weather = factoryWeather.createWeather(
                nameRegion, weatherLite.getTemperature(), weatherLite.getCreationDate()
        );
        setDelete.removeRegion(nameRegion);
        //Метод add для mapCityWeather = метод put для HashMap (в случае существования)
        //произойдет обновление
        mapCityWeather.add(weather);
        //узкое место в программе, т.к. обновление происходит за O(n)
        //В запросах List не используется, поэтому если удалить эту строчку, то ничего не сломается
        //Если этот List в дальнейшем будет использован в запросах, то я его перепишу на HashSet
        //тогда запрос будет выполняться за O(1)
        //если же использоваться не будет, то просто удалю ради оптимизации ресурсопотребления
        listWeather.update(weather);
    }
}
