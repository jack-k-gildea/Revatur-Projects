package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Response;
import models.UsersModel;
import services.UserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class UserController {
    UserServices userServices;

    private static UserController userController;
    private UserController(){
        userServices = new UserServices();
    }

    public static UserController getInstance(){
        if(userController == null){
            userController = new UserController();
        }
        return userController;
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        UsersModel user = new ObjectMapper().readValue(requestBody, UsersModel.class);

        UsersModel temp = userServices.checkLogin(user);

        if(temp == null){
            out.println(new ObjectMapper().writeValueAsString(new Response("login failed", false, null)));
        }
        else{

            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("userObj", temp);

            out.println(new ObjectMapper().writeValueAsString(new Response("login successful", true, temp)));
        }
    }

    public void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        UsersModel user = new ObjectMapper().readValue(requestBody, UsersModel.class);

        Boolean check = userServices.addUser(user);

        if(check){
            out.println(new ObjectMapper().writeValueAsString(new Response("User created successfully", true, user)));
        }
        else{
            out.println(new ObjectMapper().writeValueAsString(new Response("User already exists", false, null)));
        }
    }

    public void checkSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        UsersModel user = (UsersModel) req.getSession().getAttribute("userObj");

        if(user != null){
            out.println(new ObjectMapper().writeValueAsString(new Response("Logged in", true, user)));
        }else{
            out.println(new ObjectMapper().writeValueAsString(new Response("Not logged in", false, null)));
        }
    }
}
