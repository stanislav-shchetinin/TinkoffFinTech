package com.example.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResponseGetTemperature extends Response{
    private Double temperature;
    public ResponseGetTemperature(int codeStatus, String message, Double temperature) {
        super(codeStatus, message);
        this.temperature = temperature;
    }
}
