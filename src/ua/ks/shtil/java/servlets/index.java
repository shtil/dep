package ua.ks.shtil.java.servlets;

import ua.ks.shtil.java.db.DepartmentDBManager;
import ua.ks.shtil.java.models.Department;
import ua.ks.shtil.java.models.Employee;

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
public class index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameterMap().containsKey("dep")){
            showSelectedDepartment(req, resp);
        } else {
            showDefaultPage(req, resp);
        }



    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void showDefaultPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Department> departments = new ArrayList<>();
        Department department = new Department();

        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());


        try {
            departments = departmentDBManager.getAllDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("department", department);
        req.setAttribute("departments", departments);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }


    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void showSelectedDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String depParam = req.getParameter("dep");

        int departmentId = Integer.parseInt(depParam);

        List<Employee> employees = new ArrayList<>();
        List<Department> departments = new ArrayList<>();
        Department department = new Department();

        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());

        try {
            employees = departmentDBManager.getAllEmployee(departmentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            departments = departmentDBManager.getAllDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            department = departmentDBManager.getDepartment(departmentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("department", department);
        req.setAttribute("employees", employees);
        req.setAttribute("departments", departments);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
