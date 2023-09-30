package wrapper;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
@Getter
@Component
public class WrapperSetDelete {
    private final HashSet<String> setDeleteRegion;

    public WrapperSetDelete(){
        setDeleteRegion = new HashSet<>();
    }
    public void deleteRegion(String nameRegion){
        setDeleteRegion.add(nameRegion);
    }
}
