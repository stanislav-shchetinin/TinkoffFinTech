package org.example;

import util.ReflectionConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class FactoryWeather {
    /**
     * Ключ - имя города, значение - его идентифиактор
     * */
    private final Map<String, UUID> mapId;
    public FactoryWeather(){
        mapId = new TreeMap<>();
    }
    /**
     * Создание объекта класса Weather (при создании определяется идентификатор региона)
     * */
    public Weather createWeather(String nameRegion,
                                 Double temperature,
                                 ZonedDateTime creationData){
        try {
            Constructor<Weather> constructor = Weather.class.getDeclaredConstructor(
                    ReflectionConstants.CONSTRUCTOR_PARAMETERS
            );
            constructor.setAccessible(true);
            return constructor.newInstance(
                    getUUIDFromNameRegion(nameRegion), nameRegion, temperature, creationData
            );
        } catch (InvocationTargetException | NoSuchMethodException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Setter имени региона (идентификатор также изменяется)
     * */
    public void setWeatherNameRegion(Weather weather, String nameRegion){
        try {
            Field uuidWeather = weather.getClass().getDeclaredField(ReflectionConstants.UUID_FIELD);
            Field nameRegionWeather = Weather.class.getDeclaredField(ReflectionConstants.NAME_REGION_FIELD);
            uuidWeather.setAccessible(true);
            nameRegionWeather.setAccessible(true);
            uuidWeather.set(weather, getUUIDFromNameRegion(nameRegion));
            nameRegionWeather.set(weather, nameRegion);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

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
}
