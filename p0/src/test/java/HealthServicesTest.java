import dao.HILoginDao;
import services.HealthServices;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;

public class HealthServicesTest {

    HealthServices healthServices = new HealthServices();

    @Test
    void testCheckUsername(){
        boolean result = healthServices.hICheckUsernameAvailability("testing");
        assertFalse(result);
    }

    @Test
    void testLogin(){
        boolean result = healthServices.hICheckLogin("testing2", "password");
    }
}
