package dao;

import models.Reimbursement;

import java.util.List;

public interface ManagerDao {

    List<Reimbursement> getAllRequests();
    List<Reimbursement> getPendingRequests();
    List<Reimbursement> getAcceptedRequests();
    List<Reimbursement> getDeniedRequests();
    void acceptReimb(int request_ID, int managerID);
    void denyReimb(int request_ID, int managerID);


}
