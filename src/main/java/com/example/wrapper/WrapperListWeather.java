package com.example.wrapper;

import lombok.AllArgsConstructor;
import com.example.weather.Weather;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class WrapperListWeather{
    private List<Weather> weatherList;
    public WrapperListWeather(){
        this.weatherList = new ArrayList<>();
    }
    public void add(Weather weather){
        weatherList.add(weather);
    }
    public void addAll(ArrayList<Weather> arrayList){
        weatherList.addAll(arrayList);
    }
    public Double averageTemperatureValue(){
        return weatherList.stream().mapToDouble(Weather::getTemperature).summaryStatistics().getAverage();
    }

    public List<String> moreThen(Double temperature){
        return weatherList.stream()
                .filter(weather -> weather.getTemperature() > temperature)
                .map(Weather::getNameRegion)
                .distinct()
                .toList();
    }

    public Map<UUID, List<Double>> toMapUUIDListTemperatures(){
        return weatherList.stream()
                .collect(Collectors.groupingBy(Weather::getUuid,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public Map<Double, List<Weather>> toMapDoubleListWeather(){
        return weatherList.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature));
    }

}
