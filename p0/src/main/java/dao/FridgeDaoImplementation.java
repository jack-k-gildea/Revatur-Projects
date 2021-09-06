package dao;

import models.FridgeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FridgeDaoImplementation implements FridgeDao {

    String URL = "jdbc:postgresql://revaturedatabse.cnkcasthzrdv.us-east-2.rds.amazonaws.com/project";
    String username = "postgres";
    String password = "p4ssw0rd";

    @Override
    public List<FridgeModel> getAllFridges(String user) {
        List<FridgeModel> fridges = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "SELECT fridge_name FROM fridges WHERE username_fk = ? AND food_name = 'fridgeEntry';";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fridges.add(new FridgeModel(user, rs.getString(1), "fridgeEntry"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fridges;
    }



    public List<FridgeModel> getAllFood(String user, String fridgeName)
    {
        List<FridgeModel> fridgeContents = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "SELECT food_name FROM fridges WHERE username_fk = ? AND fridge_name = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user);
            ps.setString(2, fridgeName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fridgeContents.add(new FridgeModel(user, fridgeName, rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fridgeContents;
    }

    @Override
    public void addFridge(String user, String fridgeName) {
        try (Connection conn = DriverManager.getConnection(URL,username,password)){

            String sql = "INSERT INTO fridges VALUES (DEFAULT, ?, ?, 'fridgeEntry');";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,user);
            ps.setString(2,fridgeName);
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFridge(String user, String fridgeName) {
        try(Connection conn = DriverManager.getConnection(URL, username, password)){
            String sql = "DELETE FROM fridges WHERE username_fk = ? AND fridge_name = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2,fridgeName);
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(FridgeModel newFood) {
        try(Connection conn = DriverManager.getConnection(URL, username, password)){
            String sql = "INSERT INTO fridges VALUES (DEFAULT, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newFood.getUsername());
            ps.setString(2,newFood.getFridgename());
            ps.setString(3, newFood.getFoodItem());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeFood(FridgeModel oldFood) {
        try(Connection conn = DriverManager.getConnection(URL, username, password)){
            String sql = "DELETE FROM fridges WHERE username_fk = ? AND fridge_name = ? And food_name = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, oldFood.getUsername());
            ps.setString(2, oldFood.getFridgename());
            ps.setString(3, oldFood.getFoodItem());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Boolean checkFridgeAvailable(String user, String fridgeName) { //returns false if fridge name is already taken
        List<FridgeModel> existingFridges = getAllFridges(user);
        for(FridgeModel index: existingFridges){
            if(index.getFridgename().equals(fridgeName))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean canAddFood(String user, String fridgeName) { // returns true if fridge has less than 5 items, false if fridge is full
        int count = 0;
        List<FridgeModel> fridgeContents = getAllFood(user, fridgeName);
        for(FridgeModel index: fridgeContents){
            if(index.getFoodItem().equals("fridgeEntry")){
                continue;
            }
            else {
                count++;
            }
        }
        if(count == 5){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Boolean isEmpty(String user, String fridgeName) { //check if fridge is empty so it can be deleted, true if empty, false if >0 foodItems
        int count = 0;
        List<FridgeModel> fridgeContents = getAllFood(user, fridgeName);
        for(FridgeModel index: fridgeContents){
            if(index.getFoodItem().equals("fridgeEntry")){
                continue;
            }
            else {
                count++;
            }
        }
        if(count == 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Integer getFridgeCount(String user) { // checks number of fridges to make sure the last fridge for a restaurant is not deleted
        List<FridgeModel> fridges = getAllFridges(user);
        int count = 0;
        for(FridgeModel index: fridges){
            count++;
        }
        return count;
    }


}
