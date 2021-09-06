package services;

import dao.EmployeeDao;
import dao.EmployeeDaoImplementation;
import dao.UserDao;
import dao.UserDaoImplementation;
import models.Reimbursement;
import models.ReimbursementS;
import models.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServices {
    EmployeeDao employeeDao;
    UserDao userDao;

    public EmployeeServices(){
        employeeDao = EmployeeDaoImplementation.getInstance();
        userDao = UserDaoImplementation.getInstance();
    }

    public List<ReimbursementS> getAllRequests(int userID) {
        List<Reimbursement> reimbursementsUserInts = employeeDao.getAllRequests(userID); //gets reimbursements with int values for users
        List<UsersModel> allUsers = userDao.getAllUsers(); //gets all user models
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>(); //new arrayList for reimbursements with usernames
        String username = allUsers.get(userID-1).getUsername(); //gets employee username from userID
        for(Reimbursement index: reimbursementsUserInts){
            if(index.getReimbStatus().equals("accepted") || index.getReimbStatus().equals("denied")){ //only checks for resolver ID if request has been resolved
            String manager = allUsers.get((index.getReimbResolver())-1).getUsername(); //getting the manager account username based on userID in reimbursement
            reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //creates new reimbursement object with string values for usernames
                    index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                    index.getDescription(), username, manager, index.getReimbStatus(),
                    index.getReimbType()));
        }
            else{
                reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //if reimbursement has not been resolved
                        index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                        index.getDescription(), username, "N/A", index.getReimbStatus(),
                        index.getReimbType()));
            }
        }
        return reimbursementsUserNames;
    }

    public List<ReimbursementS> getPendingRequests(int userID){
        List<Reimbursement> reimbursementsUserInts = employeeDao.getAllRequests(userID);
        List<UsersModel> allUsers = userDao.getAllUsers();
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>();
        String username = allUsers.get((userID)-1).getUsername();
        for(Reimbursement index: reimbursementsUserInts){
            if(index.getReimbStatus().equals("pending"))
                reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(),
                        index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                        index.getDescription(), username, "N/A", index.getReimbStatus(),
                        index.getReimbType()));
        }
        return reimbursementsUserNames;
    }

    public List<ReimbursementS> getAcceptedRequests(int userID){
        List<Reimbursement> reimbursementsUserInts = employeeDao.getAllRequests(userID);
        List<UsersModel> allUsers = userDao.getAllUsers();
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>();
        String username = allUsers.get((userID)-1).getUsername();
        for(Reimbursement index: reimbursementsUserInts){
            if(index.getReimbStatus().equals("accepted"))
            reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //creates new reimbursement object with string values for usernames
                    index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                    index.getDescription(), username, allUsers.get(index.getReimbResolver()-1).getUsername(), index.getReimbStatus(),
                    index.getReimbType()));
        }
        return reimbursementsUserNames;
    }

    public List<ReimbursementS> getDeniedRequests(int userID){
        List<Reimbursement> reimbursementsUserInts = employeeDao.getAllRequests(userID);
        List<UsersModel> allUsers = userDao.getAllUsers();
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>();
        String username = allUsers.get((userID)-1).getUsername();
        for(Reimbursement index: reimbursementsUserInts){

            if(index.getReimbStatus().equals("denied"))
                reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //creates new reimbursement object with string values for usernames
                        index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                        index.getDescription(), username, allUsers.get(index.getReimbResolver()-1).getUsername(), index.getReimbStatus(),
                        index.getReimbType()));
        }
        return reimbursementsUserNames;
    }

    public boolean submitRequest(Reimbursement reimbursement) {
        if (reimbursement.getReimbAmt() == 0.0 || reimbursement.getDescription().equals("") || reimbursement.getReimbTypeInt() == 0) {
            // if one of the user submitted fields is empty, the function does nothing
            return false;
        } else {
            employeeDao.addRequest(reimbursement);
            return true;
        }
    }

    public Reimbursement getNewestReimbursement(){return employeeDao.getMostRecentEntry();}
}
