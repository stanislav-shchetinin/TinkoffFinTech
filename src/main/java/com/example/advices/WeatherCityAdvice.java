package com.example.advices;

import com.example.exceptions.NotFoundException;
import com.example.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class WeatherCityAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleException(NotFoundException e) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Response> handleException(DateTimeParseException e) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
