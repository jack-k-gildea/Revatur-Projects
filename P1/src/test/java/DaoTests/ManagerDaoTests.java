package DaoTests;

import dao.EmployeeDao;
import dao.EmployeeDaoImplementation;
import dao.ManagerDao;
import dao.ManagerDaoImplementation;
import models.Reimbursement;
import models.ReimbursementS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerDaoTests {

    ManagerDao managerDao;

    @BeforeEach
    void setup(){
        managerDao = ManagerDaoImplementation.getInstance();
    }

    @Test
    void all(){
        int accepted = 0;
        int denied = 0;
        int pending = 0;
        boolean result;
        List<Reimbursement> all = managerDao.getAllRequests();
        for(Reimbursement index: all){
            if(index.getReimbStatus().equals("accepted")){
                accepted++;
            }
            else if(index.getReimbStatus().equals("denied")){
                denied++;
            }
            else if(index.getReimbStatus().equals("pending")){
                pending++;
            }
        }
        if(accepted != 0 && denied != 0 && pending != 0){ //check to see if there is a mix of all statuses
            result = true;
        }
        else {
            result = false;
        }
        assertTrue(result);
    }

    @Test
    void getAccepted(){
        Boolean actual = true;

        List<Reimbursement> acceptedList = managerDao.getAcceptedRequests();
        for(Reimbursement index: acceptedList){
            if(index.getReimbStatus().equals("accepted")){ //if accepted just move to next entry
                continue;
            }
            else{ //if not accepted, then actual changes to false
                actual = false;
            }
        }
        //actual will only change if one of the retrieved items is not accepted
        assertTrue(actual);

    }

    @Test
    void getPending(){
        Boolean actual = true;

        List<Reimbursement> pendingList = managerDao.getPendingRequests();
        for(Reimbursement index: pendingList){
            if(index.getReimbStatus().equals("pending")){ //if pending just move to next entry
                continue;
            }
            else{ //if not pending, then actual changes to false
                actual = false;
            }
        }
        //actual will only change if one of the retrieved items is not pending
        assertTrue(actual);

    }

    @Test
    void getDenied(){
        Boolean actual = true;

        List<Reimbursement> deniedList = managerDao.getDeniedRequests();
        for(Reimbursement index: deniedList){
            if(index.getReimbStatus().equals("denied")){ //if denied just move to next entry
                continue;
            }
            else{ //if not denied, then actual changes to false
                actual = false;
            }
        }
        //actual will only change if one of the retrieved items is not denied
        assertTrue(actual);

    }

    @Test
    void accept(){
        Boolean result = false;
        int request = 2;
        managerDao.acceptReimb(request,2);
        List<Reimbursement> accepted = managerDao.getAcceptedRequests();
        for(Reimbursement index: accepted) {
            if (index.getReimbID() == request) { //if request ID is found in the list of accepted values, true
                result = true;
            } else { //if id not equal to request, continue to the next, predefined as false so no need to reset
                continue;
            }
        }
        assertTrue(result);

    }

    @Test
    void deny(){
        Boolean result = false;
        int request = 2;
        managerDao.denyReimb(request,2);
        List<Reimbursement> denied = managerDao.getDeniedRequests();
        for(Reimbursement index: denied) {
            if (index.getReimbID() == request) { //if request ID is found in the list of denied values, true
                result = true;
            } else { //if id not equal to request, continue to the next, predefined as false so no need to reset
                continue;
            }
        }
        assertTrue(result);
    }
}
