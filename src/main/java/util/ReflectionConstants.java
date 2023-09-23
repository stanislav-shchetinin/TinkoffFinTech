package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionConstants {
    public static final Class<?>[] CONSTRUCTOR_PARAMETERS = {UUID.class, String.class, Double.class, ZonedDateTime.class};
    public static final String UUID_FIELD = "uuid";
    public static final String NAME_REGION_FIELD = "nameRegion";
}
