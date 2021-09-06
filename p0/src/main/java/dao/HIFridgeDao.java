package dao;

import models.FridgeModel;
import models.HIFridgeModel;

import java.util.List;

public interface HIFridgeDao {

    List<FridgeModel> getAllFridgeItems(HIFridgeModel specificFridge);
    public List<HIFridgeModel> getAllFridges(String HIName);
    void removeFood(FridgeModel fridge);
    void addHealthInspector(HIFridgeModel health);
}
