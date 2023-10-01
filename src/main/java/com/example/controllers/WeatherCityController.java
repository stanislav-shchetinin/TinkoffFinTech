package com.example.controllers;

import com.example.exceptions.NotFoundException;
import com.example.response.Response;
import com.example.response.ResponseGetTemperature;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.requests.WeatherLiteRequest;
import com.example.weather.FactoryWeather;
import com.example.wrapper.MainWrapper;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather/{city}")
@AllArgsConstructor
@Tag(name="WeatherCityController",
        description="Контроллер методов получения температуры, загрузки/обновления погоды," +
                "удаления региона")
public class WeatherCityController {

    private final MainWrapper mainWrapper;
    private final FactoryWeather factoryWeather;

    /**
     * В качестве аргумента метод получает доту (без времени)
     * И выдает по ней температуру.
     * Если на заданую дату было несколько Weather с разным временем, то берется
     * последнее добавленное
     * */
    @Operation(
            summary = "Получение погоды",
            description = "Позволяет узнать температуру на конкретную дату, для конкретного региона"
    )
    @GetMapping
    public ResponseGetTemperature getTemperatureByDate(@PathVariable String city,
                                                                       @RequestParam String date){
        LocalDate localDate = LocalDate.parse(date);
        if (!mainWrapper.getSetDelete().isInSet(city) &&
                mainWrapper.getMapCityWeather().isInMap(city, localDate)){
            return new ResponseGetTemperature(HttpStatus.OK.value(),
                    "OK",
                    mainWrapper.getMapCityWeather().get(city, localDate));
        }
        throw new NotFoundException(String.format("Регион %s не найден", city));
    }
    @Operation(
            summary = "Опубликовать погоду",
            description = "Позволяет добавить погоду в базу"
    )
    @PostMapping
    public Response postWeather(@PathVariable String city,
                            @RequestBody WeatherLiteRequest weatherLite){
        mainWrapper.getSetDelete().removeRegion(city);
        mainWrapper.add(
                factoryWeather.createWeather(city, weatherLite.getTemperature(), weatherLite.getCreationDate())
        );
        return new Response(HttpStatus.OK.value(), "OK");
    }
    @Operation(
            summary = "Обновить погоду",
            description = "Позволяет обновить погоду в базе"
    )
    @PutMapping
    public Response putWeather(@PathVariable String city,
                            @RequestBody WeatherLiteRequest weatherLite){
        mainWrapper.getSetDelete().removeRegion(city);
        mainWrapper.update(
                factoryWeather.createWeather(city, weatherLite.getTemperature(), weatherLite.getCreationDate())
        );
        return new Response(HttpStatus.OK.value(), "OK");
    }
    /**
     * Удаление не происходит напрямую. Удаленный элемент помещается в set (с этого для пользователя он удален)
     * Если элемент нужно вернуть из этого сета он возвращается (отсюда некоторые проблемы с неймингом методов)
     * Удаление за O(1)
     * */
    @Operation(
            summary = "Удаление региона",
            description = "Удаляет регион и все связанные с ним записи"
    )
    @DeleteMapping
    public Response deleteWeather(@PathVariable String city){
        mainWrapper.getSetDelete().addRegion(city);
        return new Response(HttpStatus.OK.value(), "OK");
    }

}
