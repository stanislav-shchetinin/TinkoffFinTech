package controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather/{city}")
public class WeatherCityController {
    @GetMapping
    public Double getTemperatureByDate(@PathVariable String city){
        return 0.;
    }
}
