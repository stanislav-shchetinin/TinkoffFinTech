package org.example;

import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
        FactoryWeather factoryWeather = new FactoryWeather();
        Weather weather = factoryWeather.createWeather("Volgograd", 12.2, ZonedDateTime.now());
        Weather weather2 = factoryWeather.createWeather("Volgograd", 13.2, ZonedDateTime.now());
        Weather weather3 = factoryWeather.createWeather("Spb", 13.2, ZonedDateTime.now());
        Weather weather4 = factoryWeather.createWeather("Spb", 30.2, ZonedDateTime.now());
        Weather weather5 = factoryWeather.createWeather("Aaa", -30.0, ZonedDateTime.now());
        WrapperListWeather listWeather = new WrapperListWeather();
        listWeather.add(weather);
        listWeather.add(weather2);
        listWeather.add(weather3);
        listWeather.add(weather4);
        listWeather.add(weather5);
        System.out.println(listWeather.averageTemperatureValue());
        System.out.println(listWeather.moreThen(10.0));
        System.out.println(listWeather.toMapUUIDListTemperatures());
        System.out.println(listWeather.toMapDoubleListWeather());
    }
}