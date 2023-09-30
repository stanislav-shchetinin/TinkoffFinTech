package requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@AllArgsConstructor
public class WeatherLiteRequest {
    private Double temperature;
    private ZonedDateTime creationDate;
}
