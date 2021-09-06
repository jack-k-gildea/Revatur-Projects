package servicesTests;

import models.Reimbursement;
import models.ReimbursementS;
import org.junit.jupiter.api.*;
import services.ManagerServices;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerServicesTests {


    ManagerServices managerServices;


    @BeforeEach
    void setup(){managerServices = new ManagerServices();}

    @Test
    void getAccepted(){
        Boolean actual = true;

        List<ReimbursementS> acceptedList = managerServices.getAcceptedRequests();
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

        List<ReimbursementS> pendingList = managerServices.getPendingRequests();
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

        List<ReimbursementS> deniedList = managerServices.getDeniedRequests();
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
    void accept(){
        Boolean result = false;
        int request = 2;
        managerServices.acceptReimb(request,2);
        List<ReimbursementS> accepted = managerServices.getAcceptedRequests();
        for(ReimbursementS index: accepted) {
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
        managerServices.denyReimb(request,2);
        List<ReimbursementS> denied = managerServices.getDeniedRequests();
        for(ReimbursementS index: denied) {
            if (index.getReimbID() == request) { //if request ID is found in the list of denied values, true
                result = true;
            } else { //if id not equal to request, continue to the next, predefined as false so no need to reset
                continue;
            }
        }
        assertTrue(result);
    }

    @Test
    void all(){
        int accepted = 0;
        int denied = 0;
        int pending = 0;
        boolean result;
        List<ReimbursementS> all = managerServices.getAllRequests();
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
}



