package com.example.advices;

import com.example.exceptions.NotFoundException;
import com.example.exceptions.WeatherAPIClientException;
import com.example.exceptions.WeatherAPIServerException;
import com.example.response.Response;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherAPIAdvice {
    @ExceptionHandler(WeatherAPIServerException.class)
    public ResponseEntity<Response> handleException(WeatherAPIServerException e) {
        return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WeatherAPIClientException.class)
    public ResponseEntity<Response> handleException(WeatherAPIClientException e) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Response> handleException(RequestNotPermitted e) {
        return new ResponseEntity<>(new Response(HttpStatus.TOO_MANY_REQUESTS.value(), e.getMessage()),
                HttpStatus.TOO_MANY_REQUESTS);
    }
}
