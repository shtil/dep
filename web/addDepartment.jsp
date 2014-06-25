<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.ks.shtil.java.models.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.ks.shtil.java.db.DepartmentDBManager" %>
<%@ page import="java.util.ArrayList" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <title>Departments</title>
    <link rel="stylesheet" type="text/css" HREF="<%=request.getContextPath()%>/style/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<div id="main_container">
    <div id="header">
        <div class="logo"></div>
    </div>
    <div class="menu"></div>

    <div class="center_content">

        <div class="center_left">
            <div class="title_welcome">Personnel department</div>

            <div id="content">
                <div id="left">

                    <div class="title">Departments</div>
                    <br/>

                    <ul>
                        <c:forEach var="department" items="${departments}">
                            <li>
                                <a href="index?dep=<c:out value="${department.id}"/>">
                                    <c:out value="${department.name}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>

                    <br/><br/><br/>

                    <div style="text-align: center; display: table; margin: 0 auto;">
                        <a href="addDepartment" class="btn_add">Add Department</a>
                        <br/><br/>
                        <a href="addUser" class="btn_add">Add Employee</a>
                        <br/><br/>
                        <a href="addPosition" class="btn_add">Add Position</a>
                    </div>
                </div>

                <div id="right">
                    <div class="title">Add new department</div>
                    <br/>
                    <c:if test="${not empty warning}">
                        <div class="err">
                            <c:out value="${warning}"/>
                        </div>
                    </c:if>

                    <br/>
                    <form action="addDepartment" method="POST" accept-charset="UTF-8" >
                        Department name: <input type="text" name="name" >
                        <br />
                        <input type="submit" value="Save" />
                    </form>

                </div>
            </div>


        </div>
    </div>

    <div id="footer">
        <div class="left_footer"> Author Aleksander Milchenko <a href="mailto:shtil88@gmail.com">shtil88@gmail.com</a>
        </div>
    </div>
</div>

</body>
</html>