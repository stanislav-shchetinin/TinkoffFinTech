package wrapper;

import lombok.Getter;
import requests.CoordinatesWeatherRequest;
import weather.Weather;

import java.util.HashMap;

/**
 * Класс-обертка для HashMap, из которого за O(1) получаем ответ на GET запрос
 * */
@Getter
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
}
