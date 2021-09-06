package DaoTests;

import dao.UserDao;
import dao.UserDaoImplementation;
import models.UsersModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserServices;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoTests {
    UserDao userDao;

    public UsersModel testUser = new UsersModel(1, "jack", "password", "jack", "gildea", "jgildea467@gmail.com", 1);

    @BeforeEach
    void setup(){userDao = UserDaoImplementation.getInstance();}

    @Test
    void getUser(){

        UsersModel expected = testUser;

        UsersModel actual = userDao.getOneUser(testUser);
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());

    }

    @Test
    void getAllUsers(){
        List<UsersModel> allUsers = userDao.getAllUsers();
        boolean result = false;
        if(allUsers.size() > 1){
            result = true;
        }
        assertTrue(result);
    }
}
