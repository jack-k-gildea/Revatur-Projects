package dao;

import models.Reimbursement;
import models.UsersModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplementation implements UserDao{

    private static UserDao userDao;

    public static UserDao getInstance(){
        if(userDao == null)
            userDao = new UserDaoImplementation();
        return userDao;
    }


    private UserDaoImplementation(){
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public UsersModel getOneUser(UsersModel user) {
        UsersModel index = null;

        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?;"; //get the one entry where the username is equal to the submitted
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                index = new UsersModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public void newUser(UsersModel usersModel) {
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)){
            String sql = "INSERT INTO ers_users VALUES (DEFAULT, ?,?,?,?,?,?);"; //inserting new user based on passed values
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,usersModel.getUsername());
            ps.setString(2,usersModel.getPassword());
            ps.setString(3,usersModel.getFirstName());
            ps.setString(4,usersModel.getLastName());
            ps.setString(5,usersModel.getEmail());
            ps.setInt(6,usersModel.getRole_id());
            ps.executeUpdate();
            conn.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<UsersModel> getAllUsers() {
        List<UsersModel> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT ers_user_id, ers_username FROM ers_users ORDER BY ers_user_id;"; //select user_id and usernames from users table
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new UsersModel(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


}
