package weather;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
public class FactoryWeather {
    /**
     * Ключ - имя города, значение - его идентифиактор
     * */
    private final Map<String, UUID> mapId;
    private FactoryWeather(){
        mapId = new TreeMap<>();
    }
    /**
     * Создание объекта класса Weather (при создании определяется идентификатор региона)
     * */
    public Weather createWeather(String nameRegion,
                                 Double temperature,
                                 ZonedDateTime creationData){

        return new Weather(getUUIDFromNameRegion(nameRegion),
                nameRegion, temperature, creationData);
    }
    /**
     * Setter имени региона (идентификатор также изменяется)
     * */
    public void setWeatherNameRegion(Weather weather, String nameRegion){
        weather.setNameRegion(nameRegion);
        weather.setUuid(getUUIDFromNameRegion(nameRegion));
    }
    /**
     * Определение идентификатора по имени региона (утилитарный метод)
     * */
    private UUID getUUIDFromNameRegion(String nameRegion){
        UUID uuid;
        if (mapId.containsKey(nameRegion)){
            uuid = mapId.get(nameRegion);
        } else {
            uuid = UUID.randomUUID();
            mapId.put(nameRegion, uuid);
        }
        return uuid;
    }
    /**
     * Реализация паттерна Singleton с ленивой инициализацией и без проблем с многопоточностью
     * */
    private static class FactoryWeatherHolder {
        private final static FactoryWeather INSTANCE = new FactoryWeather();
    }
    public static FactoryWeather getInstance(){
        return FactoryWeatherHolder.INSTANCE;
    }
}
