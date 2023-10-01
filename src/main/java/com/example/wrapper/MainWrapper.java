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
    public void update(Weather weather){
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
