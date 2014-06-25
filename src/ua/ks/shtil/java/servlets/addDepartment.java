package ua.ks.shtil.java.servlets;

import ua.ks.shtil.java.db.DepartmentDBManager;
import ua.ks.shtil.java.models.Department;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milchenko Aleksander
 *
 * email: shtil88@gmail.com
 */
public class addDepartment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        mainForward(req, resp);

    }

    private void mainForward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> departments = new ArrayList<>();

        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());

        try {
            departments = departmentDBManager.getAllDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("departments", departments);
        req.getRequestDispatcher("addDepartment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String departmentName = req.getParameter("name").trim();

        if (!departmentName.equals("")) {

            DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());
            try {
                departmentDBManager.saveNewDepartment(departmentName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mainForward(req, resp);
        } else {
            warningForward(req, resp);
        }



    }

    private void warningForward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String warning = "Please write correct department!";
        req.setAttribute("warning", warning);

        mainForward(req, resp);
    }
}
