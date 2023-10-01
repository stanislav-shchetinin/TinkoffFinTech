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
    public void addRegion(String nameRegion){
        setDeleteRegion.add(nameRegion);
    }
    public boolean isInSet(String nameRegion){
        return setDeleteRegion.contains(nameRegion);
    }
}
