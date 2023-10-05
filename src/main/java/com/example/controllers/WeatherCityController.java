package com.example.controllers;

import com.example.exceptions.NotFoundException;
import com.example.response.Response;
import com.example.response.ResponseGetTemperature;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.requests.WeatherLiteRequest;
import com.example.services.WeatherCityService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather/{city}")
@AllArgsConstructor
@Tag(name="WeatherCityController",
        description="Контроллер методов получения температуры, загрузки/обновления погоды," +
                "удаления региона")
public class WeatherCityController {

    private final WeatherCityService weatherCityService;

    /**
     * В качестве аргумента метод получает доту (без времени)
     * И выдает по ней температуру.
     * Если на заданую дату было несколько Weather с разным временем, то берется
     * последнее добавленное
     * */
    @Operation(
            summary = "Получение погоды",
            description = "Позволяет узнать температуру на конкретную дату, для конкретного региона." +
                    "Если на заданую дату было несколько Weather с разным временем, то берется последнее добавленное."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Температура найдена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найден Weather с данными параметрами"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "localDate задан некорректно"
            )
    })
    @GetMapping
    public ResponseGetTemperature getTemperatureByDate(@PathVariable String city,
                                                                       @RequestParam LocalDate localDate){
        if (weatherCityService.isEntryInBase(city, localDate)){
            return new ResponseGetTemperature(HttpStatus.OK.value(),
                    "OK",
                    weatherCityService.getTemperature(city, localDate));
        }
        throw new NotFoundException(String.format("Регион %s не найден", city));
    }
    @Operation(
            summary = "Опубликовать погоду",
            description = "Позволяет добавить погоду в базу"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Город добавлен"
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void postWeather(@PathVariable String city,
                            @RequestBody WeatherLiteRequest weatherLite){
        weatherCityService.add(city, weatherLite);
    }
    @Operation(
            summary = "Обновить погоду",
            description = "Позволяет обновить погоду в базе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Город обновлен"
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping
    public void putWeather(@PathVariable String city,
                            @RequestBody WeatherLiteRequest weatherLite){
        weatherCityService.update(city, weatherLite);
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Город удален"
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping
    public void deleteWeather(@PathVariable String city){
        weatherCityService.deleteRegion(city);
    }

}
