package dao;

import models.HILoginModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HILoginDaoImplementation implements HILoginDao {

    String URL = "jdbc:postgresql://revaturedatabse.cnkcasthzrdv.us-east-2.rds.amazonaws.com/project";
    String username = "postgres";
    String password = "p4ssw0rd";
    @Override
    public boolean checkLogin(HILoginModel hi) { //return true if successful login, false if username/password not in system
        List<HILoginModel> HIUser = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL,username,password)){
            String sql = "SELECT * FROM hIUsers;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                HIUser.add(new HILoginModel(rs.getString(1),rs.getString(2)));
            }
            conn.close();
            for(HILoginModel index: HIUser) {
                String name = index.getUsername();
                String pass = index.getPassword();
                if (name.equals(hi.getUsername()) && pass.equals(hi.getPassword())) {
                    return true;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void addUser(HILoginModel hi) {
        try (Connection conn = DriverManager.getConnection(URL,username,password)){

            String sql = "INSERT INTO hIUsers VALUES (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hi.getUsername());
            ps.setString(2, hi.getPassword());
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkUsername(String given) { //returns true if username already exists in the server, false if username available
        List<String> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, username, password)) {
            String sql = "SELECT username FROM hIUsers;";
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
