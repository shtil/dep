package ua.ks.shtil.java.servlets;

import ua.ks.shtil.java.db.DepartmentDBManager;
import ua.ks.shtil.java.models.Department;
import ua.ks.shtil.java.models.Employee;
import ua.ks.shtil.java.models.Position;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Milchenko Aleksander
 *
 * email: shtil88@gmail.com
 */
public class addPosition extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<Department> departmentList = new ArrayList<>();
        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());
        try {
            departmentList = departmentDBManager.getAllDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("departments", departmentList);
        req.getRequestDispatcher("addPosition.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BigDecimal minSalary = null;
        BigDecimal maxSalary = null;
        String name = "";
        int department = 0;

        if (req.getParameter("name") != null) {
            name = req.getParameter("name").trim();
        }
        if (req.getParameter("department") != null) {
            try {
                department = Integer.parseInt(req.getParameter("department"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        if (req.getParameter("minSalary") != null) {
            try {
                minSalary = new BigDecimal(req.getParameter("minSalary"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (req.getParameter("maxSalary") != null) {
            try {
                maxSalary = new BigDecimal(req.getParameter("maxSalary"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (name.equals("") || department == 0 || minSalary == null || maxSalary == null || (Integer.parseInt(maxSalary.subtract(minSalary).toString())) < 0) {
            String warning = "Please write correct department!";
            req.setAttribute("warning", warning);
            doGet(req, resp);
            return;
        }

        Position position = new Position();
        position.setName(name);
        position.setDepartment(department);
        position.setMinSalary(minSalary);
        position.setMaxSalary(maxSalary);

        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());
        try {
            departmentDBManager.saveNewPosition(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("index").forward(req, resp);
    }

}
