package services;

import dao.*;
import models.*;

import java.util.List;


public class RestServices {
     FridgeDao fridgeDao = new FridgeDaoImplementation();
     UsersDao usersDao = new UsersDaoImplementation();
  //  public FridgeDao fridgeDao1;

    public RestServices() {
    }

   // public RestServices(FridgeDao fridgeDao1) {this.fridgeDao1 = fridgeDao1;}

    public void restUserCreateAcct(String username, String password) {
            usersDao.addUser(new UsersModel(username,password));
            System.out.println("Account created, please login");
    }


    public Boolean checkUsername(String username){ // returns false if the username is taken, true if available
        return usersDao.checkUsername(username);
    }

    public Boolean restUserCheckLogin(String username, String password) { // returns false if username/password combo is not in system, true if successful login
        return (usersDao.checkLogin(new UsersModel(username,password)));
    }

    public void restGetFridges(String username)
    {
        List<FridgeModel> availableFridges = fridgeDao.getAllFridges(username);
        for(FridgeModel index: availableFridges){
            if(index.getFoodItem().equals("fridgeEntry")){
                System.out.println(index.toStringFridges());
            }
        }
    }

    public Boolean checkFridgeName(String username, String fridgeName){ //returns false if fridge name is already in use for that restaurant
        return fridgeDao.checkFridgeAvailable(username,fridgeName);
    }

    public Boolean canAddItems(String username, String fridgeName){ //returns false if fridge is full (has 5 items), true if less than 5
        return fridgeDao.canAddFood(username, fridgeName);
    }

    public void restAddFridge(String username, String fridgeName){
        fridgeDao.addFridge(username, fridgeName);
    }

    public void restAddFood(FridgeModel newFood){
        if(fridgeDao.canAddFood(newFood.getUsername(), newFood.getFridgename())) {
            fridgeDao.addFood(newFood);
        }
    }

    public void viewFood(String username, String fridgeName){
        List<FridgeModel> foodItems = fridgeDao.getAllFood(username,fridgeName);
        for(FridgeModel index: foodItems){
            if(index.getFoodItem().equals("fridgeEntry") && foodItems.size() == 1){
                System.out.println("\nFridge is empty \n");
                continue;
            }
            else if(index.getFoodItem().equals("fridgeEntry")){
                continue;
            }
            else {
                System.out.println(index.toStringFood());
            }
        }
    }
    public void removeFood(FridgeModel removeItem){
        fridgeDao.removeFood(removeItem);
    }

    public boolean isEmpty(String username, String fridgeName){ return fridgeDao.isEmpty(username, fridgeName); }  //Returns true if fridge is empty

    public void deleteFridge(String username, String fridgeName) {
        fridgeDao.deleteFridge(username, fridgeName);
    }

    public void moveFood(FridgeModel moving, String newFridge){
        if(fridgeDao.canAddFood(moving.getUsername(), newFridge)){ //check if fridge is full
            restAddFood(new FridgeModel(moving.getUsername(), newFridge, moving.getFoodItem()));
            removeFood(moving);
        }
        else{
            System.out.println("Fridge is full");
        }
    }

    public boolean moveAllFood(String username, String oldFridge, String newFridge){ //returns true if all food from old fridge is moved, false if new fridge filled up before all items were transferred
        boolean completed = true;
        List<FridgeModel> foodItems= fridgeDao.getAllFood(username,oldFridge);
        for(FridgeModel index: foodItems){
            if(index.getFoodItem().equals("fridgeEntry")){ //If it is the entry for just the fridge, move to the next entry
                continue;
            }
            else {
                if(fridgeDao.canAddFood(username, newFridge)) { //if the fridge has room, add item
                    moveFood(index, newFridge);
                }
                else{ // if the fridge is full, break out
                    System.out.println("Fridge is full");
                    completed = false;
                    break;
                }
            }
            }
        return completed;
    }

    public void restGetFridgesExceptCurrentOrFull(String username, String fridgeName)
    {
        List<FridgeModel> availableFridges = fridgeDao.getAllFridges(username);
        for(FridgeModel index: availableFridges){
            if(availableFridges.size() > 1){
                if(index.getFridgename().equals(fridgeName)){ // skips printing currently selected fridge
                    continue;
                }
                if(fridgeDao.canAddFood(username, index.getFridgename())) { //only displays fridges that are not full
                    System.out.println(index.toStringFridges());
                }
            }
            else{
                System.out.println("No available fridges");
            }
        }
    }

    public int getFridgeCount(String user){ return fridgeDao.getFridgeCount(user);} //returns number of fridges owned by restaurant, to make sure they are not deleting their last fridge

    public boolean checkFoodName(FridgeModel food){
        List<FridgeModel> foodItems = this.fridgeDao.getAllFood(food.getUsername(), food.getFridgename());
        for(FridgeModel index: foodItems){
            if(index.getFoodItem().equals(food.getFoodItem())){
                return true;
            }
        }
        return false;
    }
}
