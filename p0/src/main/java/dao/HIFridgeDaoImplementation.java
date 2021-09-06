package dao;

import models.FridgeModel;
import models.HIFridgeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HIFridgeDaoImplementation implements HIFridgeDao {
    String URL = "jdbc:postgresql://revaturedatabse.cnkcasthzrdv.us-east-2.rds.amazonaws.com/project";
    String username = "postgres";
    String password = "p4ssw0rd";

    @Override
    public List<FridgeModel> getAllFridgeItems(HIFridgeModel specificFridge) {
        List<FridgeModel> fridgeContents = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "SELECT fr.food_name FROM fridges fr INNER JOIN HIFridges hi ON hi.rest_user = ? AND hi.access_given = fr.fridge_name AND hi.hiusername = ? AND hi.access_given = ?;  ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, specificFridge.getRest_user());
            ps.setString(2, specificFridge.getHIUsername());
            ps.setString(3, specificFridge.getAccess_given());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fridgeContents.add(new FridgeModel(specificFridge.getRest_user(), specificFridge.getAccess_given(), rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fridgeContents;
    }

    public List<HIFridgeModel> getAllFridges(String HIName)
    {
        List<HIFridgeModel> fridgeNames = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "SELECT fr.username_fk, fr.fridge_name FROM fridges fr INNER JOIN HIFridges hi ON hi.access_given = fr.fridge_name AND hi.hiusername = ? AND fr.food_name = 'fridgeEntry';";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, HIName);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                fridgeNames.add(new HIFridgeModel(HIName, rs.getString(1), rs.getString(2)));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            return fridgeNames;
    }

    @Override
    public void removeFood(FridgeModel fridge) {
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "DELETE FROM fridges WHERE food_name = ? AND username_fk = ? AND fridge_name = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fridge.getFoodItem());
            ps.setString(2, fridge.getUsername());
            ps.setString(3, fridge.getFridgename());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addHealthInspector(HIFridgeModel health) {
        try(Connection conn = DriverManager.getConnection(URL,username,password)){
            String sql = "INSERT INTO HIFridges VALUES (DEFAULT, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, health.getHIUsername());
            ps.setString(2, health.getRest_user());
            ps.setString(3, health.getAccess_given());
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
