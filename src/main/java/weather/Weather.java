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
     * <p>Не стоит использовать Setter uuid напрямую из Weather, т.к. это может сломать связь nameRegion -- uuid</p>
     * <p>Поля устанавливается при вызове setter nameRegion в FactoryWeather</p>
     * */
    private UUID uuid;
    /**
     * <p>Не стоит использовать Setter напрямую из Weather, т.к. в нем не реализована смена uuid</p>
     * <p>Setter поля nameRegion реалиозован в FactoryWeather, который порадил этот объект</p>
     * */
    @Setter(value = AccessLevel.PACKAGE)
    private String nameRegion;
    @Setter
    private Double temperature;
    @Setter
    private ZonedDateTime creationDate;

}
