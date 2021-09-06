package dao;

import models.Reimbursement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDaoImplementation implements ManagerDao{

    private static ManagerDao managerDao;

    public static ManagerDao getInstance(){
        if(managerDao == null)
            managerDao = new ManagerDaoImplementation();
        return managerDao;
    }

    private ManagerDaoImplementation(){
        try {
        Class.forName("org.postgresql.Driver");
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }
}


    @Override
    public List<Reimbursement> getAllRequests() {
        List<Reimbursement> allReimbursements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT er.reimb_id, er.reimb_amt, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_author , er.reimb_resolver , ers.reimb_status, ert.reimb_type FROM ers_reimbursement er \n" + //columns that we want to retrieve
                    "                    INNER JOIN ers_reimbursement_status ers ON er.reimb_status = ers.reimb_status_id \n" + //inner join with lookup table to get specific status instead of number
                    "                    INNER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id\n" +  //inner join with reimbursement type to get type of reimbursement
                    "                    ORDER BY er.reimb_status, er.reimb_author;"; //order by reimbursement status then by user


            PreparedStatement ps = conn.prepareStatement(sql);
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
    public List<Reimbursement> getPendingRequests() {
        List<Reimbursement> pendingReimbursements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT er.reimb_id, er.reimb_amt, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_author , er.reimb_resolver , ers.reimb_status, ert.reimb_type FROM ers_reimbursement er \n" + //specific columns
                    "INNER JOIN ers_reimbursement_status ers ON er.reimb_status = ers.reimb_status_id \n" + //get status from lookup table
                    "INNER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id\n" + //get type from lookup table
                    "WHERE ers.reimb_status_id = 2 ORDER BY er.reimb_status, er.reimb_author;"; //get specific status, in this case pending
            PreparedStatement ps = conn.prepareStatement(sql);
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
    public List<Reimbursement> getAcceptedRequests() {
        List<Reimbursement> acceptedReimbursements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT er.reimb_id, er.reimb_amt, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_author , er.reimb_resolver , ers.reimb_status, ert.reimb_type FROM ers_reimbursement er \n" +
                    "INNER JOIN ers_reimbursement_status ers ON er.reimb_status = ers.reimb_status_id \n" + //get status as string instead of int
                    "INNER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id\n" + //get type as string instead of int
                    "WHERE ers.reimb_status_id = 3 ORDER BY er.reimb_status, er.reimb_author;"; //specific parameter of accepted, order by author
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acceptedReimbursements.add( new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4),rs.getString(5),
                        rs.getInt(6), rs.getInt(7),
                        rs.getString(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acceptedReimbursements;
    }

    @Override
    public List<Reimbursement> getDeniedRequests() {
        List<Reimbursement> deniedReimbursements = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url, ConnectionUtil.username, ConnectionUtil.password)) {
            String sql = "SELECT er.reimb_id, er.reimb_amt, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_author , er.reimb_resolver , ers.reimb_status, ert.reimb_type FROM ers_reimbursement er \n" +
                    "INNER JOIN ers_reimbursement_status ers ON er.reimb_status = ers.reimb_status_id \n" +
                    "INNER JOIN ers_reimbursement_type ert ON er.reimb_type_id = ert.reimb_type_id\n" +
                    "WHERE ers.reimb_status_id = 1 ORDER BY er.reimb_status, er.reimb_author;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                deniedReimbursements.add( new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4),rs.getString(5),
                        rs.getInt(6), rs.getInt(7),
                        rs.getString(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deniedReimbursements;
    }

    @Override
    public void acceptReimb(int request_ID, int managerID) {
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url,ConnectionUtil.username,ConnectionUtil.password)){

            String sql = "UPDATE ers_reimbursement SET reimb_resolved = current_timestamp WHERE reimb_id = ?;"; //set the time resolved
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, request_ID);
            ps.executeUpdate();

            String sql2 = "UPDATE ers_reimbursement SET reimb_status = 3 WHERE reimb_id = ?;"; //set the reimbursement to accepted
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, request_ID);
            ps2.executeUpdate();

            String sql3 = "UPDATE ers_reimbursement SET reimb_resolver = ? WHERE reimb_id = ?;"; //set the reimbursement manager ID for specified request
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1,managerID);
            ps3.setInt(2, request_ID);
            ps3.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void denyReimb(int request_ID, int managerID) {
        try (Connection conn = DriverManager.getConnection(ConnectionUtil.url,ConnectionUtil.username,ConnectionUtil.password)){

            String sql = "UPDATE ers_reimbursement SET reimb_resolved = current_timestamp WHERE reimb_id = ?;"; //update time
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, request_ID);
            ps.executeUpdate();

            String sql2 = "UPDATE ers_reimbursement SET reimb_status = 1 WHERE reimb_id = ?;"; //update status
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, request_ID);
            ps2.executeUpdate();

            String sql3 = "UPDATE ers_reimbursement SET reimb_resolver = ? WHERE reimb_id = ?;"; //set the resolver to the managerID
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1,managerID);
            ps3.setInt(2, request_ID);
            ps3.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
