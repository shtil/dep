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
public class addUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Department> departments = new ArrayList<>();
        List<Position> positions = new ArrayList<>();
        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());

        try {
            departments = departmentDBManager.getAllDepartments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            positions = departmentDBManager.getAllPositions();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("departments", departments);
        req.setAttribute("positions", positions);
        req.getRequestDispatcher("addUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name ="";
        String birthday = "";
        String passportNumber = "";
        int department = 0;
        int position = 0;
        BigDecimal salary = null;

        if (req.getParameter("name")!=null) {
            name = req.getParameter("name").trim();
        }

        if (req.getParameter("birthday")!= null) {
            birthday = req.getParameter("birthday").trim();
        }
        if (req.getParameter("passportNumber")!=null) {
            passportNumber = req.getParameter("passportNumber").trim();
        }
        if (req.getParameter("department")!=null) {
           try {
               department = Integer.parseInt(req.getParameter("department"));
           }catch (NumberFormatException e){
               e.printStackTrace();
           }
        }
        if (req.getParameter("position")!=null) {
            try {
                position = Integer.parseInt(req.getParameter("position"));
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        if (req.getParameter("salary")!=null) {
            try {
                salary = new BigDecimal(req.getParameter("salary"));
            } catch ( NumberFormatException e){
                e.printStackTrace();
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(req.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*
        TODO  Need check salary in range
         */

        if (name.equals("") || date ==null || passportNumber.equals("") || department==0 || position == 0 || salary == null){

            String warning = "Please write correct data";
            req.setAttribute("warning", warning);
            doGet(req,resp);
            return;
        }

        Employee employee = new Employee();
        employee.setName(name);
        employee.setBirthday(date);
        employee.setPassportNumber(passportNumber);

        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());

        Department mDepartment = new Department();
        Position mPosition = new Position();
        try {
            mDepartment = departmentDBManager.getDepartment(department);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            mPosition = departmentDBManager.getPosition(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        employee.setDepartment(mDepartment);
        employee.setPosition(mPosition);
        employee.setSalary(salary);


        try {
            departmentDBManager.saveNewUser(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String url = "index?dep="+employee.getDepartment().getId();

        req.getRequestDispatcher(url).forward(req, resp);
    }

}
