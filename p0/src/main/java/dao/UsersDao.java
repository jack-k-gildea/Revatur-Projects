package dao;

import models.UsersModel;

public interface UsersDao {
    void addUser(UsersModel user);
    boolean checkLogin(UsersModel user);
    public boolean checkUsername(String given);
}
