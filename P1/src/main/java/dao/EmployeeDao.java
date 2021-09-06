package dao;

import models.Reimbursement;

import java.util.List;

public interface EmployeeDao {
    List<Reimbursement> getAllRequests(int userID);
    List<Reimbursement> getPendingRequests(int userID);
    void addRequest(Reimbursement reimbursement);
    Reimbursement getMostRecentEntry();
}
