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

            <script type="text/javascript">
                <!--
                var aPositionValues = new Array();
                var aDepartmentValues = new Array();

                <c:forEach items="${positions}" var="position">
                aPositionValues.push([
                    <c:out value="${position.department}"/>,
                    <c:out value="${position.id}"/>,
                    "<c:out value="${position.name}"/>",
                    <c:out value="${position.minSalary}"/>,
                    <c:out value="${position.maxSalary}"/>
                ]);
                </c:forEach>

                <c:forEach items="${departments}" var="department">
                aDepartmentValues.push(<c:out value="${department.id}"/>);
                </c:forEach>

                function getId(index) {
                    return aDepartmentValues[index];
                }

                function getPositionsValuesById(index) {
                    var array = new Array();
                    for (i = 0; i < aPositionValues.length; i++) {
                        if (aPositionValues[i][0] == index) {
                            array.push(aPositionValues[i]);
                        }
                    }
                    return array;
                }

                function MkHouseValues(index) {
                    var id = getId(index);
                    var aCurrPositionValues = getPositionsValuesById(id);
                    var nCurrPositionValuesCnt = aCurrPositionValues.length;
                    var oPositionList = document.forms["employee"].elements["position"];
                    var oHouseListOptionsCnt = oPositionList.options.length;
                    oPositionList.length = 0;
                    for (i = 0; i < nCurrPositionValuesCnt; i++) {
                        if (document.createElement) {
                            var newHouseListOption = document.createElement("OPTION");
                            newHouseListOption.text = aCurrPositionValues[i][2];
                            newHouseListOption.value = aCurrPositionValues[i][1];
                            (oPositionList.options.add) ? oPositionList.options.add(newHouseListOption) : oPositionList.add(newHouseListOption, null);
                        } else {
                            oPositionList.options[i] = new Option(aCurrPositionValues[i][2], aCurrPositionValues[i][1], false, false);
                        }
                    }
                }

                MkHouseValues(document.forms["employee"].elements["department"].selectedIndex);


                function change(index) {

                    var position = aPositionValues[index];

                    document.getElementById('validSalary').innerHTML = "* Min salary " + aPositionValues[index][3] + "<br/>Max salary " + aPositionValues[index][4];
                }
                //-->
            </script>


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
                    <div class="title">Editing employee</div>
                    <br/>
                    <c:if test="${not empty warning}">
                        <div class="err">
                            <c:out value="${warning}"/>
                        </div>
                    </c:if>

                    <form name="employee" action="editUser" method="POST" accept-charset="UTF-8">
                        <input type="hidden" name="id" value="<c:out value="${employee.id}"/>"><br/><br/>

                        <strong>Name:</strong> <c:out value="${employee.name}"/><br/><br/>
                        <strong> Birthday:</strong> <c:out value="${employee.birthday}"/><br/><br/>
                        <strong>Passport number:</strong> <c:out value="${employee.passportNumber}"/><br/><br/>
                        <strong>Department:</strong> <c:out value="${employee.department.name}"/><br/><br/>
                        <select name="department" onChange="MkHouseValues(this.selectedIndex)">
                            <c:forEach items="${departments}" var="department">
                                <option value="<c:out value="${department.id}"/>"
                                        <c:if test="${ department.id == employee.department.id}">
                                            selected
                                        </c:if>>
                                    <c:out value="${department.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                        <br/><br/>
                        <strong>Position: </strong>
                        <select name="position" onClick="change(this.selectedIndex)">
                            <option value="<c:out value="${employee.position.id}"/>">
                                <c:out value="${employee.position.name}"/>
                            </option>
                        </select>
                        <br/><br/>
                        <strong>Salary: </strong>
                        <input type="text" name="salary" value="<c:out value="${employee.salary}"/>"><br/><br/>

                        <div id="validSalary"></div>
                        <input type="submit" value="Save"/>

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

<!-- end of main_container -->
</body>
</html>
