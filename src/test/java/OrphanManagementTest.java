import com.example.WeatherApplication;
import com.example.dao.WeatherServiceHibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrphanManagementTest {

    @Autowired
    private WeatherServiceHibernate weatherServiceHibernate;

    @Test
    public void saveTest() {

    }
}
