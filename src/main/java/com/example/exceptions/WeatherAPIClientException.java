package com.example.exceptions;

public class WeatherAPIClientException extends RuntimeException{
    public WeatherAPIClientException(String message){
        super(message);
    }
}
