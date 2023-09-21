package org.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
public class Weather {
    //использовать патерн Observer для триггера id при изменении nameRegion
    private int id;

    private String nameRegion;
    @Setter
    private Double temperature;
    @Setter
    private ZonedDateTime creationDate;
    public Weather(@NonNull String nameRegion,
                   @NonNull Double temperature,
                   @NonNull ZonedDateTime creationDate){
        this.nameRegion = nameRegion;
        this.id = this.nameRegion.hashCode();
        this.temperature = temperature;
        this.creationDate = creationDate;
    }
    public void setNameRegion(@NonNull String nameRegion) {
        this.nameRegion = nameRegion;
        this.id = nameRegion.hashCode();
    }
}
