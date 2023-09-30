package wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weather.Weather;

@Component
@AllArgsConstructor
@Getter
public class MainWrapper {
    private WrapperListWeather listWeather;
    private WrapperMapCityWeather mapCityWeather;
    private WrapperSetDelete setDelete;

    public void add(Weather weather){
        listWeather.add(weather);
        mapCityWeather.add(weather);
    }
    public void deleteRegion(String nameRegion){
        setDelete.addRegion(nameRegion);
    }
}
