package dao;

import models.UsersModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDaoImplementation implements UsersDao{

    String URL = "jdbc:postgresql://revaturedatabse.cnkcasthzrdv.us-east-2.rds.amazonaws.com/project";
    String username = "postgres";
    String password = "p4ssw0rd";

    @Override
    public void addUser(UsersModel user) {
        try (Connection conn = DriverManager.getConnection(URL,username,password)){

            String sql = "INSERT INTO restaurant_users VALUES (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    @Override
    public boolean checkLogin(UsersModel user){ // returns false if username/password combo is not in system, true if successful login
        List<UsersModel> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL,username,password)){
            String sql = "SELECT * FROM restaurant_users;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            users.add(new UsersModel(rs.getString(1),rs.getString(2)));
            }
            conn.close();
            for(UsersModel index: users) {
                String name = index.getUsername();
                String pass = index.getPassword();
                if (name.equals(user.getUsername()) && pass.equals(user.getPassword())) {
                    return true;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean checkUsername(String given) { // returns true if the username is taken, false if available
        List<String> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "SELECT username FROM restaurant_users;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(rs.getString(1));
            }
            conn.close();
            for (String index : users) {
                if (index.equals(given)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
