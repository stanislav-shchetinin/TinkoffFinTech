package org.example;

import lombok.AllArgsConstructor;
import java.util.*;
@AllArgsConstructor
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
        Map<UUID, List<Double>> result = new HashMap<>();
        weatherList.forEach(weather -> result.computeIfAbsent(
                weather.getUuid(), k -> new ArrayList<>()).add(weather.getTemperature())
        );
        return result;
    }

    public Map<Double, List<Weather>> toMapDoubleListWeather(){
        Map<Double, List<Weather>> result = new HashMap<>();
        weatherList.forEach(weather ->  result.computeIfAbsent(
                weather.getTemperature(), k -> new ArrayList<>()).add(weather)
        );
        return result;
    }

}
