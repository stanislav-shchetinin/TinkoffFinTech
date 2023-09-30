package requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
@Getter
@AllArgsConstructor
public class CoordinatesWeatherRequest {
    private String nameRegion;
    private ZonedDateTime creationDate;
}
