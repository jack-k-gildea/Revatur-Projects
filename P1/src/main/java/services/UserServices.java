package services;

import dao.UserDao;
import dao.UserDaoImplementation;
import models.UsersModel;


public class UserServices {
    UserDao userDao;

    public UserServices(){
        userDao = UserDaoImplementation.getInstance();
    }

    public UsersModel checkLogin(UsersModel usersModel){
        UsersModel temp = userDao.getOneUser(usersModel);

        String name = temp.getUsername();
        String pass = temp.getPassword();
        if (name.equals(usersModel.getUsername()) && pass.equals(usersModel.getPassword())) {  //check the retrieved username and password to the provided username and password, if true, user is returned, if false null returned
            return temp;
        }
        return null;

    }

    public boolean addUser(UsersModel usersModel){
        if(userDao.getOneUser(usersModel) == null) { //if username is not in the system add user, functionality abandoned (thought it was requirement, it was not)
            userDao.newUser(usersModel);
            return true;
        }
        return false;
    }


}
