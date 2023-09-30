package requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * Класс для объединения данных для GET запроса.
 * Он является ключом HashMap (WrapperMapCityWeather),
 * по которому получаем ответ на запрос
 * */
@Getter
@AllArgsConstructor
public class CoordinatesWeatherRequest {
    private String nameRegion;
    private ZonedDateTime creationDate;
}
