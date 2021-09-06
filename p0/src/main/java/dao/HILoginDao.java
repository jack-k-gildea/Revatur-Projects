package dao;

import models.HILoginModel;

public interface HILoginDao {
    boolean checkLogin(HILoginModel hi);
    void addUser(HILoginModel hi);
    public boolean checkUsername(String given);
}
