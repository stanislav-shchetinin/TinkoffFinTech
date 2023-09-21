package org.example;

import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * <p>Базовый класс</p>
 * <p>Конструктор приватный, чтобы было единственное место для создания объекта - FactoryWeather
 * (это уменьшает риск создания некорретного идентификатора). Создание объекта в фабрике реализовано
 * через Reflection API</p>
 * <p>Setter для nameRegion аналогичным образом сделан в фабрике</p>
 * */
@Getter
@ToString
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class Weather {
    /**
     * <p>Setter этого поля отсутсвует, т.к. идентификатор зависит от nameRegion</p>
     * Устанавливается в конструкторе, т.к. значение поля uuid задает, пораждающая фабрика
     * */
    private UUID uuid;
    /**
     * Setter поля nameRegion реалиозован в FactoryWeather, который порадил этот объект
     * */
    private String nameRegion;
    @Setter
    private Double temperature;
    @Setter
    private ZonedDateTime creationDate;
}
