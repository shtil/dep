<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <title>Departments</title>
    <link rel="stylesheet" type="text/css" HREF="<%=request.getContextPath()%>/style/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
                        <c:forEach items="${departments}" var="department">
                            <li>
                                <a href="index?dep=<c:out value="${department.id}" />">
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
                    <div class="title">
                        <c:choose>
                            <c:when test="${department.id == 0}">
                                Welcome
                            </c:when>
                            <c:when test="${department.name!= null}">
                                Users of <c:out value="${department.name}"/>
                            </c:when>
                        </c:choose>
                    </div>
                    <br/>

                    <c:choose>
                        <c:when test="${department.id ==0 && empty employee}">
                            <div style="margin-top: 50px;">
                                <strong>
                                    Welcome to the site management personnel department.<br/>
                                    Select the department to continue
                                </strong>
                            </div>
                        </c:when>

                        <c:when test="${empty employees}">
                            <br/>

                            <div class="title">No users</div>
                            <br/>
                        </c:when>
                        <c:when test="${not empty employees}">
                            <c:forEach items="${employees}" var="employee">
                                <div style=" display: table; margin: 0 auto; width: 390px; padding-bottom: 10px;">
                                    <div style=" float:left;">
                                        <strong>Name: </strong><c:out value="${employee.name}"/><br/>
                                        <strong>Position: </strong><c:out value="${employee.position.name}"/>
                                    </div>
                                    <div style="float: right;">
                                        <a class="btn"
                                           onclick="return confirm('Are you sure you want to delete this item?');"
                                           href="deleteUser?id=<c:out value="${employee.id}" />&dep=<c:out value="${employee.department.id}" />">
                                            Delete
                                        </a>
                                    </div>
                                    <div style="float: right; margin-right: 5px;">
                                        <a class="btn" href="editUser?id=<c:out value="${employee.id}" />">
                                            Edit
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>

                    </c:choose>


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




