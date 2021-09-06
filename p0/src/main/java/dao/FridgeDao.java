package dao;

import models.FridgeModel;

import java.util.List;

public interface FridgeDao {

    List<FridgeModel> getAllFridges(String user);
    List<FridgeModel> getAllFood(String username, String fridgeName);
    void addFridge(String user, String fridgeName);
    void deleteFridge(String user, String fridgeName);
    void addFood(FridgeModel newFood);
    void removeFood(FridgeModel oldFood);
    Boolean checkFridgeAvailable(String user, String fridgeName);
    Boolean canAddFood(String user, String fridgeName);
    Boolean isEmpty(String user, String fridgeName);
    Integer getFridgeCount(String user);
}
