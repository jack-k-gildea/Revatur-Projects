package servicesTests;

import dao.UserDao;
import dao.UserDaoImplementation;
import models.UsersModel;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import services.UserServices;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTests {

    UserServices userServices;

    public UsersModel testUser = new UsersModel(1, "jack", "password", "jack", "gildea", "jgildea467@gmail.com", 1);

    @BeforeEach
    void setup(){userServices = new UserServices();}

    @Test
    void getUser(){

        UsersModel expected = testUser;

        UsersModel actual = userServices.checkLogin(testUser);
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());

    }
}
