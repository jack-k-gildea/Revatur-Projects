package dao;

import models.Reimbursement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImplementation implements EmployeeDao{

    private static EmployeeDao employeeDao;

    public static EmployeeDao getInstance(){
        if(employeeDao == null){
            employeeDao = new EmployeeDaoImplementation();
        }
        return employeeDao;
    }

    private EmployeeDaoImplementation(){
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
    }
    }
    @Override
    public List<Reimbursement> getAllRequests(int userID) {
        List<Reimbursement> allReimbursements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT er.reimb_id, er.reimb_amt, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_author , er.reimb_resolver , ers.reimb_status, ert.reimb_type FROM ers_reimbursement er " +
                    "INNER JOIN ers_reimbursement_status ers ON er.reimb_status = ers.reimb_status_id " + //get specific status instead of int value
                    "INNER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id " + //get specific type instead of int value
                    "WHERE er.reimb_author = ? ORDER BY er.reimb_status;"; //get requests based on the author, order by denied, pending, then accepted
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allReimbursements.add( new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4),rs.getString(5),
                        rs.getInt(6), rs.getInt(7),
                        rs.getString(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allReimbursements;
    }

    @Override
    public List<Reimbursement> getPendingRequests(int userID) {
        List<Reimbursement> pendingReimbursements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT er.reimb_id, er.reimb_amt, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_author , er.reimb_resolver , ers.reimb_status, ert.reimb_type FROM ers_reimbursement er \n" + //specific columns
                    "INNER JOIN ers_reimbursement_status ers ON er.reimb_status = ers.reimb_status_id \n" + //get status from lookup table
                    "INNER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id\n" + //get type from lookup table
                    "WHERE er.reimb_author = ? AND ers.reimb_status_id = 2;"; //get specific status, in this case pending, for specific user
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pendingReimbursements.add( new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4),rs.getString(5),
                        rs.getInt(6), rs.getInt(7),
                        rs.getString(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingReimbursements;
    }

    @Override
    public void addRequest(Reimbursement reimbursement) {
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)){
            String sql = "INSERT INTO ers_reimbursement VALUES (DEFAULT, ?, DEFAULT, NULL, ?, ?, NULL, 2,?);"; //set values that will be the same for all new requests (ID and current time default, resolve time and resolve manager null as it is a new request, and status is always 2 because new requests are always pending)
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1,reimbursement.getReimbAmt()); //setting values given by user such as amount, description, and type given by user, author given after login from URLparam
            ps.setString(2,reimbursement.getDescription());
            ps.setInt(3,reimbursement.getReimbAuthor());
            ps.setInt(4,reimbursement.getReimbTypeInt());
            ps.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Reimbursement getMostRecentEntry(){
        Reimbursement reimbursement = new Reimbursement();
        try(Connection conn = DriverManager.getConnection(ConnectionUtil.url,ConnectionUtil.username,ConnectionUtil.password)){
            String sql = "SELECT * FROM ers_reimbursement er ORDER BY er.reimb_id DESC LIMIT 1;"; //gets the newest entry based on the most recent ID
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                reimbursement = new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4),rs.getString(5),
                        rs.getInt(6), rs.getInt(7),
                        rs.getInt(8), rs.getInt(9));
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return reimbursement;
    }


}
