import dao.FridgeDao;
import dao.HILoginDao;
import models.FridgeModel;
import services.HealthServices;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import services.RestServices;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RestServicesTests {

    RestServices restServices = new RestServices();

    @Test
    void checkLogin(){
        boolean result =restServices.restUserCheckLogin("testing", "password");
        assertTrue(result);
    }

    @Test
    void checkFridgeCount(){
        int expected = 4;
        int actual = restServices.getFridgeCount("testing");
        assertEquals(expected, actual);
    }

    @Test
    void checkFoodNameTest(){

        boolean result = restServices.checkFoodName(new FridgeModel("testing","testing","beef"));
        assertEquals(true, result);
    }

    @Test
    void testFull(){
        boolean result = restServices.canAddItems("testing","testFull");
        assertFalse(result);
    }

    @Test
    void testEmpty(){
        boolean result = restServices.isEmpty("testing","testEmpty");
        assertTrue(result);
    }

    @Test
    void testCheckIfUsernameAalreadyExists(){
        boolean result = restServices.checkUsername("testing");
        assertTrue(result);
    }



     /*   FridgeDao fridgeDao = Mockito.mock(FridgeDao.class);

    @BeforeEach
    void setup(){
        restServices = new RestServices(fridgeDao);
    }

    @Test
    void checkFoodNameTest(){
        List<FridgeModel> foodItems = new ArrayList<>();
        foodItems.add(1,new FridgeModel("jack", "meat", "beef"));
        foodItems.add(2,new FridgeModel("jack","meat", "bacon"));
        Mockito.when(fridgeDao.getAllFood("jack", "meat")).thenReturn(foodItems);

        Boolean actual = restServices.checkFoodName(new FridgeModel("jack","meat","beef"));

        assertEquals(true,actual);
    }*/
}
