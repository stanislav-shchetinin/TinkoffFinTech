package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class FactoryWeather {
    private final Map<String, UUID> mapId;
    public FactoryWeather(){
        mapId = new TreeMap<>();
    }
    public Weather createWeather(String nameRegion,
                                 Double temperature,
                                 ZonedDateTime creationData){
        try {
            Constructor<Weather> constructor = Weather.class.getDeclaredConstructor(
                    UUID.class, String.class, Double.class, ZonedDateTime.class
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
    public void setWeatherNameRegion(Weather weather, String nameRegion){
        try {
            Field uuidWeather = weather.getClass().getDeclaredField("uuid");
            Field nameRegionWeather = Weather.class.getDeclaredField("nameRegion");
            uuidWeather.setAccessible(true);
            nameRegionWeather.setAccessible(true);
            uuidWeather.set(weather, getUUIDFromNameRegion(nameRegion));
            nameRegionWeather.set(weather, nameRegion);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

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
