package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Response;
import services.ManagerServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerController {

    ManagerServices managerServices;
    private static ManagerController managerController;

    private ManagerController(){
        managerServices = new ManagerServices();
    }

    public static ManagerController getInstance(){
        if(managerController == null)
            managerController = new ManagerController();

        return managerController;
    }

    public void getAllRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, managerServices.getAllRequests())));
    }

    public void getPendingRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, managerServices.getPendingRequests())));
    }

    public void getAcceptRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, managerServices.getAcceptedRequests())));
    }

    public void getDeniedRequests(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        out.println(new ObjectMapper().writeValueAsString(new Response("Returned Lists", true, managerServices.getDeniedRequests())));
    }

    public void acceptReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        int reimbID = Integer.parseInt(req.getParameter("id")); //get values from URLparam id
        int managerID = Integer.parseInt(req.getParameter("userid")); //get value from URLparam userID

        managerServices.acceptReimb(reimbID,managerID);
        out.println(new ObjectMapper().writeValueAsString(new Response("reimbursement accepted", true, null)));
    }

    public void denyReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        int reimbID = Integer.parseInt(req.getParameter("id"));
        int managerID = Integer.parseInt(req.getParameter("userid"));

        managerServices.denyReimb(reimbID,managerID);
        out.println(new ObjectMapper().writeValueAsString(new Response("reimbursement accepted", true, null)));
    }
}
