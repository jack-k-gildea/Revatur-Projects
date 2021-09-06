package DaoTests;

import dao.EmployeeDao;
import dao.EmployeeDaoImplementation;
import models.Reimbursement;
import org.junit.jupiter.api.*;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeDaoTests {

EmployeeDao employeeDao;

    @BeforeEach
    void setup(){
        employeeDao = EmployeeDaoImplementation.getInstance();
    }

    @Test
    void getPending(){
        Boolean actual = true;

        List<Reimbursement> pendingList = employeeDao.getPendingRequests(1);
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
    void all(){
        int accepted = 0;
        int denied = 0;
        int pending = 0;
        boolean result;
        List<Reimbursement> all = employeeDao.getAllRequests(1);
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
    void submit(){ //kind of tests both submit and get most recent
        boolean result = false;
        Reimbursement submit = new Reimbursement(0, 1002.03, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), "from the test", 1, 0, 2, 2);
        employeeDao.addRequest(submit);
        Reimbursement newest = employeeDao.getMostRecentEntry();
        if(submit.getDescription().equals(newest.getDescription())){
            result = true;
        }
        assertTrue(result);
    }

}
