package ua.ks.shtil.java.db;

/**
 * Created by Milchenko Aleksander
 *
 * email: shtil88@gmail.com
 */

import ua.ks.shtil.java.models.Department;
import ua.ks.shtil.java.models.Employee;
import ua.ks.shtil.java.models.Position;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class DepartmentDBManager {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    ServletContext context = null;

    /**
     *
     * @param context
     */
    public DepartmentDBManager(ServletContext context) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.context = context;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {

        String dbUser = context.getInitParameter("dbUser");
        String dbPassword = context.getInitParameter("dbPassword");
        String dbUrl = context.getInitParameter("dbURL");

     //   return  DriverManager.getConnection("jdbc:mysql://localhost/personnel_department?useUnicode=true&characterEncoding=UTF-8", "shtil", "shtil27101988");
        return  DriverManager.getConnection(dbUrl, dbUser, dbPassword);

    }

    /**
     *
     * @return
     * @throws SQLException
     */

    public List<Position> getAllPositions() throws SQLException {

        List<Position> positions = new ArrayList<>();
        try (Connection c = getConnection();
             Statement st = c.createStatement()
        ) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM position");

            while (resultSet.next()) {
                Position position = new Position();
                position.setId(resultSet.getInt("id"));
                position.setName(resultSet.getString("name"));
                position.setMinSalary(resultSet.getBigDecimal("minSalary"));
                position.setMaxSalary(resultSet.getBigDecimal("maxSalary"));
                position.setDepartment(resultSet.getInt("department"));
                positions.add(position);
                }
        }
        return positions;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public List<Department> getAllDepartments() throws SQLException {

        List<Department> departments = new ArrayList<>();
        try (Connection c = getConnection();
             Statement st = c.createStatement()
        ) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM department");

            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                departments.add(department);

            }
        }
        return departments;
    }

    /**
     *
     * @param name
     * @throws SQLException
     */
    public void saveNewDepartment(String name) throws SQLException {

        try (Connection c = getConnection();
             Statement st = c.createStatement()
        ) {
            PreparedStatement stmt = c.prepareStatement( "INSERT INTO department  (name) VALUES (?)");
            stmt.setString(1, name);
            stmt.execute();
        }
    }

    /**
     *
     * @param employee
     * @throws SQLException
     */
    public void updateUser(Employee employee) throws SQLException {
        try (Connection c = getConnection();
            PreparedStatement stmt = c.prepareStatement( "UPDATE employee SET department_id = (?), position_id = (?), salary = (?)  WHERE  id= (?)");
        ) {
            stmt.setInt(1, employee.getDepartment().getId());
            stmt.setInt(2, employee.getPosition().getId());
            stmt.setBigDecimal(3, employee.getSalary());
            stmt.setInt(4, employee.getId());
            stmt.execute();
        }
    }

    /**
     *
     * @param employee
     * @throws SQLException
     */
    public void saveNewUser(Employee employee) throws SQLException {
        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement( "INSERT INTO employee  (name, birthday, passportNumber,department_id, position_id, salary) VALUES (?,?,?,?,?,?)");
        ) {
            stmt.setString(1,employee.getName());
            stmt.setDate(2, new Date(employee.getBirthday().getDate()));
            stmt.setString(3, employee.getPassportNumber());
            stmt.setInt(4, employee.getDepartment().getId());
            stmt.setInt(5, employee.getPosition().getId());
            stmt.setBigDecimal(6, employee.getSalary());

            stmt.execute();
        }
    }

    /**
     *
     * @param position
     * @throws SQLException
     */
    public void saveNewPosition(Position position) throws  SQLException{

        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement( "INSERT INTO position (name,department,minSalary,maxSalary ) VALUES (?,?,?,?)");
        ) {
            stmt.setString(1,position.getName());
            stmt.setInt(2, position.getDepartment());
            stmt.setBigDecimal(3, position.getMinSalary());
            stmt.setBigDecimal(4, position.getMaxSalary());

            stmt.execute();
        }
    }

    /**
     *
     * @param departmentId
     * @return
     * @throws SQLException
     */
    public Department getDepartment(int departmentId) throws SQLException {

        Department department = new Department();
        String sql = "SELECT * FROM department WHERE id =(?)";
        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
        ) {
            stmt.setInt(1, departmentId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
            }
        }
        return department;
    }

    /**
     *
     * @param positionId
     * @return
     * @throws SQLException
     */
    public Position getPosition(int positionId) throws SQLException {

        Position position = new Position();
        String sql = "SELECT * FROM position WHERE id = (?)";

        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
        ) {
            stmt.setInt(1, positionId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                position.setId(resultSet.getInt("id"));
                position.setName(resultSet.getString("name"));
                position.setMinSalary(resultSet.getBigDecimal("minSalary"));
                position.setMaxSalary(resultSet.getBigDecimal("maxSalary"));
            }
        }
        return position;
    }

    /**
     *
     * @param departmentID
     * @return
     * @throws SQLException
     */
    public List<Employee> getAllEmployee(int departmentID) throws SQLException {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.*, d.*, p.* FROM employee e INNER JOIN department d ON e.department_id=d.id INNER JOIN position p ON e.position_id=p.id WHERE e.department_id = (?)";
        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
        ) {
            stmt.setInt(1, departmentID);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                Department department = new Department();
                Position position = new Position();

                employee.setId(resultSet.getInt("e.id"));
                employee.setName(resultSet.getString("e.name"));
                employee.setBirthday(resultSet.getDate("e.birthday"));
                employee.setPassportNumber(resultSet.getString("e.passportNumber"));

                department.setId(resultSet.getInt("d.id"));
                department.setName(resultSet.getString("d.name"));

                employee.setDepartment(department);

                position.setId(resultSet.getInt("p.id"));
                position.setName(resultSet.getString("p.name"));
                position.setDepartment(resultSet.getInt("department"));
                position.setMinSalary(resultSet.getBigDecimal("p.minSalary"));
                position.setMaxSalary(resultSet.getBigDecimal("p.maxSalary"));

                employee.setPosition(position);

                employees.add(employee);

            }
        }
        return employees;
    }

    /**
     *
     * @param employeeId
     * @return
     * @throws SQLException
     */
    public Employee getEmployee(int employeeId) throws SQLException {

        Employee employee = new Employee();
        try (Connection c = getConnection();
             Statement st = c.createStatement()
        ) {
            ResultSet resultSet = st
                    .executeQuery("SELECT e.id, e.name, e.birthday, e.passportNumber, e.salary,e.position_id, d.id, d.name, p.id, p.name, p.department, p.minSalary, p.maxSalary FROM employee e INNER JOIN department d ON e.department_id=d.id INNER JOIN position p ON e.position_id=p.id WHERE e.id = "+employeeId);

            while (resultSet.next()) {

                Department department = new Department();
                Position position = new Position();

                employee.setId(resultSet.getInt("e.id"));
                employee.setName(resultSet.getString("e.name"));
                employee.setBirthday(resultSet.getDate("e.birthday"));
                employee.setPassportNumber(resultSet.getString("e.passportNumber"));

                department.setId(resultSet.getInt("d.id"));
                department.setName(resultSet.getString("d.name"));

                employee.setDepartment(department);

                position.setId(resultSet.getInt("p.id"));
                position.setName(resultSet.getString("p.name"));
                position.setDepartment(resultSet.getInt("department"));
                position.setMinSalary(resultSet.getBigDecimal("p.minSalary"));
                position.setMaxSalary(resultSet.getBigDecimal("p.maxSalary"));

                employee.setPosition(position);
            }
        }
        return employee;
    }

    /**
     *
     * @param id
     * @throws SQLException
     */
    public void removeEmployee(int id)  throws SQLException{

        String sql = "DELETE FROM employee WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }
}
