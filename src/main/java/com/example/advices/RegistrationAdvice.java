package com.example.advices;

import com.example.exceptions.UserAlreadyExistsException;
import com.example.exceptions.WeatherAPIServerException;
import com.example.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationAdvice {
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Response> handleException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.CONFLICT.value(), "User with this username already exists"),
                HttpStatus.CONFLICT);
    }
}
