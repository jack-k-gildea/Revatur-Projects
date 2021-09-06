package dao;

import models.UsersModel;

import java.util.List;

public interface UserDao {

    UsersModel getOneUser(UsersModel usersModel);
    void newUser(UsersModel usersModel);
    List<UsersModel> getAllUsers();
}
