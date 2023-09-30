package wrapper;

import lombok.Getter;

import java.util.HashSet;
@Getter
public class WrapperSetDelete {
    private final HashSet<String> setDeleteRegion;

    public WrapperSetDelete(){
        setDeleteRegion = new HashSet<>();
    }
    public void deleteRegion(String nameRegion){
        setDeleteRegion.add(nameRegion);
    }
}
