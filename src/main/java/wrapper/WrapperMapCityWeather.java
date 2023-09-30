package wrapper;

import lombok.Getter;
import org.springframework.stereotype.Component;
import requests.CoordinatesWeatherRequest;
import weather.Weather;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Класс-обертка для HashMap, из которого за O(1) получаем ответ на GET запрос
 * */
@Component
public class WrapperMapCityWeather {
    private final HashMap<CoordinatesWeatherRequest, Double> hashMap;
    public WrapperMapCityWeather(){
        hashMap = new HashMap<>();
    }
    public void add(Weather weather){
        hashMap.put( new CoordinatesWeatherRequest(
                weather.getNameRegion(), weather.getCreationDate()),
                weather.getTemperature());
    }
    public Double get(String nameRegion, ZonedDateTime zonedDateTime){
        return hashMap.get(new CoordinatesWeatherRequest(nameRegion, zonedDateTime));
    }
}
