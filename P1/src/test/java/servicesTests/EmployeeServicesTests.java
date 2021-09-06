package servicesTests;

import models.Reimbursement;
import models.ReimbursementS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.EmployeeServices;
import services.ManagerServices;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeServicesTests {

    EmployeeServices employeeServices;
    ManagerServices managerServices;

    @BeforeEach
    void setup(){
        employeeServices = new EmployeeServices();
        managerServices = new ManagerServices();
    }

    @Test
    void getAccepted(){
        Boolean actual = true;

        List<ReimbursementS> acceptedList = employeeServices.getAcceptedRequests(1);
        for(ReimbursementS index: acceptedList){
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

        List<ReimbursementS> pendingList = employeeServices.getPendingRequests(1);
        for(ReimbursementS index: pendingList){
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

        List<ReimbursementS> deniedList = employeeServices.getDeniedRequests(1);
        for(ReimbursementS index: deniedList){
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
    void all(){
        int accepted = 0;
        int denied = 0;
        int pending = 0;
        boolean result;
        List<ReimbursementS> all = employeeServices.getAllRequests(1);
        for(ReimbursementS index: all){
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
    void getNewest(){
        boolean result = true;
        Reimbursement newest = employeeServices.getNewestReimbursement();
        List<ReimbursementS> all = managerServices.getAllRequests();
        for(ReimbursementS index: all){
            if(newest.getReimbID() < index.getReimbID()){ //only changes result if one of the list has a higher ID, making "newest" not the newest
                result = false;
            }
        }
        assertTrue(result);
    }

    @Test
    void submit(){
        Reimbursement submit = new Reimbursement(0, 1002.03, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), "from the test", 1, 0, 2, 2);
        boolean result = employeeServices.submitRequest(submit);

        assertTrue(result);
    }
}
