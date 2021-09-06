package dispatcher;

import controllers.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/api/*")
public class Dispatcher extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String URI = req.getRequestURI();


        switch(URI){ // determine method based on uri of fetch request
            case "/api/login":
                if(req.getMethod().equals("POST"))
                    UserController.getInstance().login(req, resp);
                break;
            case "/api/addUser":
                if(req.getMethod().equals("POST"))
                    UserController.getInstance().addUser(req, resp);
                break;
            case "/api/employee":
                switch(req.getMethod()){ //multiple methods based on same uri so switch based on method sent
                    case "GET":
                        EmployeeController.getInstance().getAllRequests(req, resp);
                        break;
                    case "POST":
                        EmployeeController.getInstance().submitRequest(req, resp);
                        break;
                }
                break;
            case "/api/employee/pending":
                EmployeeController.getInstance().getPendingRequests(req, resp);
                break;
            case "/api/employee/accepted":
                EmployeeController.getInstance().getAcceptedRequests(req, resp);
                break;
            case "/api/employee/denied":
                EmployeeController.getInstance().getDeniedRequests(req, resp);
                break;
            case "/api/manager":
                switch(req.getMethod()) { //multiple methods based on same uri so switch based on method sent
                    case "GET":
                        ManagerController.getInstance().getAllRequests(req, resp);
                        break;
                    case "POST":
                        ManagerController.getInstance().acceptReimbursement(req, resp);
                        break;
                    case "PATCH":
                        ManagerController.getInstance().denyReimbursement(req, resp);
                        break;
                }
                break;
            case "/api/manager/accepted":
                ManagerController.getInstance().getAcceptRequests(req, resp);
                break;
            case "/api/manager/denied":
                ManagerController.getInstance().getDeniedRequests(req, resp);
                break;
            case "/api/manager/pending":
                ManagerController.getInstance().getPendingRequests(req, resp);
                break;
            case "/api/checkSession":
                if(req.getMethod().equals("GET"))
                    UserController.getInstance().checkSession(req, resp);
                break;
        }
    }
}
























