package com.example.services;

import com.example.requests.WeatherLiteRequest;

import java.time.LocalDate;

public class WeatherCityStub implements  WeatherCityBehavior{
    @Override
    public void add(String nameRegion, WeatherLiteRequest weatherLite) {

    }

    @Override
    public boolean isRegionInBase(String nameRegion) {
        return true;
    }

    @Override
    public boolean isEntryInBase(String nameRegion, LocalDate localDate) {
        return true;
    }

    @Override
    public boolean deleteRegion(String nameRegion) {
        return false;
    }

    @Override
    public Double getTemperature(String nameRegion, LocalDate localDate) {
        return -10.;
    }

    @Override
    public void update(String nameRegion, WeatherLiteRequest weatherLite) {

    }
}
