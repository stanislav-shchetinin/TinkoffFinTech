package com.example.wrapper;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
@Component
public class WrapperSetDelete {
    private final HashSet<String> setDeleteRegion;

    public WrapperSetDelete(){
        setDeleteRegion = new HashSet<>();
    }
    /**
     * Этот метод ДОБАВЛЯЕТ в set УДАЛЕННЫХ регионов (фактически удаляет из программы,
     * но в контексте set'а добавляет)
     * */
    public void addRegion(String nameRegion){
        setDeleteRegion.add(nameRegion);
    }
    /**
     * Этот метод УДАЛЯЕТ из set'а УДАЛЕННЫХ регионов (фактически возвращает регион в программу,
     * но в контексте set'а удаляет)
     * */
    public void removeRegion(String nameRegion) {
        setDeleteRegion.remove(nameRegion);
    }
    public boolean isInSet(String nameRegion){
        return setDeleteRegion.contains(nameRegion);
    }
}
