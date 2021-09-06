package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Reimbursement;
import models.ReimbursementS;
import models.Response;
import services.EmployeeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class EmployeeController {

    EmployeeServices employeeServices;
    private static EmployeeController employeeController;

    private EmployeeController(){
        employeeServices = new EmployeeServices();
    }

    public static EmployeeController getInstance(){
        if(employeeController == null)
            employeeController = new EmployeeController();

        return employeeController;
    }

    public void getAllRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Integer userID = Integer.parseInt(req.getParameter("id"));
        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, employeeServices.getAllRequests(userID))));
    }

    public void getPendingRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Integer userID = Integer.parseInt(req.getParameter("id"));
        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, employeeServices.getPendingRequests(userID))));
    }

    public void getAcceptedRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Integer userID = Integer.parseInt(req.getParameter("id"));
        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, employeeServices.getAcceptedRequests(userID))));
    }

    public void getDeniedRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Integer userID = Integer.parseInt(req.getParameter("id"));
        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, employeeServices.getDeniedRequests(userID))));
    }

    public void submitRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Reimbursement reimbursement = new ObjectMapper().readValue(requestBody, Reimbursement.class);
        employeeServices.submitRequest(reimbursement);
        Reimbursement test = employeeServices.getNewestReimbursement();
        if(reimbursement.getDescription().equals(test.getDescription()) && reimbursement.getReimbAmt() == test.getReimbAmt()) {
            out.println(new ObjectMapper().writeValueAsString(new Response("request submitted", true, null)));
        }
        else{
            out.println(new ObjectMapper().writeValueAsString(new Response("request failed to submit", false, null)));
        }
    }
}
