package services;

import dao.ManagerDao;
import dao.ManagerDaoImplementation;
import dao.UserDao;
import dao.UserDaoImplementation;
import models.Reimbursement;
import models.ReimbursementS;
import models.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class ManagerServices {
    ManagerDao managerDao;
    UserDao userDao;

    public ManagerServices(){
        managerDao = ManagerDaoImplementation.getInstance();
        userDao = UserDaoImplementation.getInstance();
    }

   public  List<ReimbursementS> getAllRequests(){
        List<Reimbursement> reimbursementsUserInts = managerDao.getAllRequests(); //gets reimbursements with int values for users
        List<UsersModel> allUsers = userDao.getAllUsers(); //gets all user models
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>(); //new arrayList for reimbursements with usernames
        for(Reimbursement index: reimbursementsUserInts){
            String username = allUsers.get((index.getReimbAuthor())-1).getUsername(); //gets employee username from userID
            if(index.getReimbStatus().equals("accepted") || index.getReimbStatus().equals("denied")){ //only checks for resolver ID if request has been judged
                String manager = allUsers.get((index.getReimbResolver())-1).getUsername(); //getting the manager account username based on reimbursement userID
                reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //creates new reimbursement object with string values for usernames
                        index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                        index.getDescription(), username, manager, index.getReimbStatus(),
                        index.getReimbType()));
            }
            else{
                reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //if reimbursement has not been judged, creates reimbursement objects with N/A for manager userID
                        index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                        index.getDescription(), username, "N/A", index.getReimbStatus(),
                        index.getReimbType()));
            }
        }
        return reimbursementsUserNames;
    }
    public List<ReimbursementS> getPendingRequests(){
        List<Reimbursement> reimbursementsUserInts = managerDao.getPendingRequests();
        List<UsersModel> allUsers = userDao.getAllUsers();
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>();
        for(Reimbursement index: reimbursementsUserInts){
            String username = allUsers.get((index.getReimbAuthor())-1).getUsername(); //changes user ints to user strings for display
            reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(),
                    index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                    index.getDescription(), username, "N/A", index.getReimbStatus(),
                    index.getReimbType()));
        }
        return reimbursementsUserNames;
    }
    public List<ReimbursementS> getAcceptedRequests(){
        List<Reimbursement> reimbursementsUserInts = managerDao.getAcceptedRequests();
        List<UsersModel> allUsers = userDao.getAllUsers();
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>();
        for(Reimbursement index: reimbursementsUserInts){
            String username = allUsers.get((index.getReimbAuthor())-1).getUsername();
            String manager = allUsers.get((index.getReimbResolver())-1).getUsername(); //getting the manager account username based on userID in reimbursement
            reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //creates new reimbursement object with string values for usernames
                    index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                    index.getDescription(), username, manager, index.getReimbStatus(),
                    index.getReimbType()));
        }
        return reimbursementsUserNames;
    }
   public List<ReimbursementS> getDeniedRequests(){
        List<Reimbursement> reimbursementsUserInts = managerDao.getDeniedRequests();
        List<UsersModel> allUsers = userDao.getAllUsers();
        List<ReimbursementS> reimbursementsUserNames = new ArrayList<>();
        for(Reimbursement index: reimbursementsUserInts){
            String username = allUsers.get((index.getReimbAuthor())-1).getUsername();
            String manager = allUsers.get((index.getReimbResolver())-1).getUsername(); //getting the manager account username based on userID in reimbursement
            reimbursementsUserNames.add(new ReimbursementS(index.getReimbID(), //creates new reimbursement object with string values for usernames
                    index.getReimbAmt(), index.getSubmitted(),index.getResolved(),
                    index.getDescription(), username, manager, index.getReimbStatus(),
                    index.getReimbType()));
        }
        return reimbursementsUserNames;
    }
   public void acceptReimb(int request_ID, int managerID){
        managerDao.acceptReimb(request_ID, managerID);
    }

   public void denyReimb(int request_ID, int managerID){
        managerDao.denyReimb(request_ID, managerID);
    }
}
