package ua.ks.shtil.java.servlets;

import ua.ks.shtil.java.db.DepartmentDBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Milchenko Aleksander
 *
 * email: shtil88@gmail.com
 */
public class deleteUser extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        DepartmentDBManager departmentDBManager = new DepartmentDBManager(getServletContext());

        try {
            departmentDBManager.removeEmployee(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("index").forward(req, resp);

    }
}
