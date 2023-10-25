package com.example.exceptions;

public class WeatherAPIServerException extends RuntimeException{
    public WeatherAPIServerException(String message){
        super(message);
    }
}
