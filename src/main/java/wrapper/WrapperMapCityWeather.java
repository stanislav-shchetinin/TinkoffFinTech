package wrapper;

import lombok.Getter;
import requests.CoordinatesWeatherRequest;
import weather.Weather;

import java.util.HashMap;
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
