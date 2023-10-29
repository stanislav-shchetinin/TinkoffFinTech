package com.example.services;

import com.example.requests.WeatherLiteRequest;

import java.time.LocalDate;

public interface WeatherCityBehavior {
    void add(String nameRegion, WeatherLiteRequest weatherLite);
    boolean isRegionInBase(String nameRegion);
    boolean isEntryInBase(String nameRegion, LocalDate localDate);
    boolean deleteRegion(String nameRegion);
    Double getTemperature(String nameRegion, LocalDate localDate);
    void update(String nameRegion, WeatherLiteRequest weatherLite);
}
