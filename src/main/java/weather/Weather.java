package weather;

import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * <p>Базовый класс</p>
 * <p>Конструктор package-private. Не стоит создавать объект класса напрямую через new
 * лучше воспользоваться FactoryWeather</p>
 * <p>Setter для nameRegion аналогичным образом сделан в фабрике</p>
 * */
@Getter
@ToString
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class Weather {
    /**
     * <p>Setter этого поля отсутсвует, т.к. идентификатор зависит от nameRegion</p>
     * Устанавливается в конструкторе, т.к. значение поля uuid задает, пораждающая фабрика
     * */
    private UUID uuid;
    /**
     * <p>Setter поля nameRegion реалиозован в FactoryWeather, который порадил этот объект</p>
     * <p>Не стоит использовать Setter напрямую из Weather, т.к. в нем не реализована смена uuid</p>
     * */
    @Setter(value = AccessLevel.PACKAGE)
    private String nameRegion;
    @Setter
    private Double temperature;
    @Setter
    private ZonedDateTime creationDate;

}
