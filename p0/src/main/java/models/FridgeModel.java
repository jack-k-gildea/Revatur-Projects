package models;
public class FridgeModel implements Comparable<FridgeModel> {

    private String username;
    private String fridgeName;
    private String foodItem;

    public FridgeModel(String username, String fridgeName, String foodItem) {
        this.username = username;
        this.fridgeName = fridgeName;
        this.foodItem = foodItem;
    }

    public FridgeModel() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFridgename() {
        return fridgeName;
    }

    public void setFridgename(String fridgename) {
        this.fridgeName = fridgename;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    @Override
    public String toString() {
        return "fridges{" +
                "username='" + username + '\'' +
                ", fridgename='" + fridgeName + '\'' +
                ", foodItem='" + foodItem + '\'' +
                '}';
    }

    public String toStringFridges() {
        return "FridgeModel{" +
                "username='" + username + '\'' +
                ", fridgeName='" + fridgeName + '\'' +
                '}';
    }

    public String toStringFood() {
        return "foodItem='" + foodItem + '\'';
    }

    @Override
    public int compareTo(FridgeModel o) {
        if(this.fridgeName.charAt(0) < o.fridgeName.charAt(0)) {
            return -1;
        }
        else{
            return 1;
        }
    }
}