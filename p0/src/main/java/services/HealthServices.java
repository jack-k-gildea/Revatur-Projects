package services;

import dao.*;
import models.*;

import java.util.Collections;
import java.util.List;


public class HealthServices {

    HIFridgeDao hiFridgeDao = new HIFridgeDaoImplementation();
    HILoginDao hiLoginDao = new HILoginDaoImplementation();

    public HealthServices() {
    }

    public void hICreateAcct(String username, String password) {
            hiLoginDao.addUser(new HILoginModel(username,password));
            System.out.println("Account created, please login");
        }



    public Boolean hICheckUsernameAvailability(String username) {//returns true if username already exists in the server, false if username available
        return hiLoginDao.checkUsername(username);
    }

    public Boolean hICheckLogin(String username, String password) { //returns true if login successful
        return hiLoginDao.checkLogin(new HILoginModel(username,password));
    }


    public void hIGetFridges(String HIUsername) {
        List<HIFridgeModel> availableFridges = hiFridgeDao.getAllFridges(HIUsername);
        if(availableFridges.size() == 0){
            System.out.println("No fridges assigned");
        }
        else{
        for(int index = 0; index< availableFridges.size(); index++) {
                System.out.println(availableFridges.get(index).toString());
        }
        }
    }

    public boolean checkFridgeName(HIFridgeModel check){  //returns true if user has access to fridgeName
        List<HIFridgeModel> availableFridges = hiFridgeDao.getAllFridges(check.getHIUsername());
        for(HIFridgeModel index: availableFridges){
            if(index.getRest_user().equals(check.getRest_user()) && index.getAccess_given().equals(check.getAccess_given())){
                return true;
            }
        }
        return false;
    }

    public void hIGetFridgeFood(HIFridgeModel specificFridge) {
        List<FridgeModel> fridgeContents = hiFridgeDao.getAllFridgeItems(specificFridge);
        for(FridgeModel index: fridgeContents)
        {
            if(index.getFoodItem().equals("fridgeEntry") && fridgeContents.size() == 1) //if the only entry is the fridgeEntry, there are no food Items
            {
                System.out.println("\n Fridge is empty \n");
            }
            else if(index.getFoodItem().equals("fridgeEntry")){ //skips entry where foodItem = fridgeEntry
                continue;
        }
            else {
                System.out.println(index);
            }
        }
    }

    public void hIDeleteFood(FridgeModel foodToDelete) {
        hiFridgeDao.removeFood(foodToDelete);
    }


    public void addHI(HIFridgeModel health){  hiFridgeDao.addHealthInspector(health);  }
}
