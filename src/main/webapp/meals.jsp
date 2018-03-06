<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meals</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
</head>
<body>
<div class="container bg-light" style="padding-top:20px;padding-bottom:20px;">
    <div class="row"><h1>Meals | <a href="index.html">Home</a></h1></div>
</div>
<div class="container bg-light" style="padding-top:20px;padding-bottom:20px;">
    <div class="row">
        <div class="col-sm-4">
            <form role="form" method="post" action="meals">
                <div class="form-group">
                    <input type="hidden" name="id" value="<c:out value="${meal != null ? meal.getId() : ''}"/>">
                    <label> Description </label>
                    <input type="text" class="form-control" name="description"
                           value="<c:out value="${meal != null ? meal.getDescription() : ''}"/>">
                </div>
                <div class="form-group">
                    <label> Time </label>
                    <input type="datetime-local" class="form-control" name="dateTime"
                           value="<c:out value="${meal != null ? meal.getDateTime() : ''}"/>">
                </div>
                <div class="form-group">
                    <label> Calories </label>
                    <input type="text" class="form-control" name="calories"
                           value="<c:out value="${meal != null ? meal.getCalories() : ''}"/>">
                </div>
                <div class="form-group">
                    <label> Action </label>
                    <c:choose>
                    <c:when test="${action == 'update'}">
                    <button class="btn btn-info btn-block btn-success" type="submit"> Update</button>
                    <input type="hidden" name="mode" value="update">
                    </c:when>
                    <c:otherwise>
                    <button class="btn btn-info btn-block btn-success" type="submit"> Add</button>
                    <input type="hidden" name="mode" value="insert">
                    </c:otherwise>
                    </c:choose>
            </form>
        </div>
    </div>

    <div class="col-sm-8">
        <table class="table" border="0" width="100%" style="font-weight: bold;font-family: Arial">
            <h3>Daily calories limit is : <a style="font-weight: bold; color: red"><c:out value="${dailyLimit}"/>
            </h3>
            <tr align="center">
                <td>id</td>
                <td>Description</td>
                <td>Calories</td>
                <td>Date</td>
                <td>Actions</td>
            </tr>
            <c:forEach items="${meals}" var="mealWithExceed">
                <jsp:useBean id="mealWithExceed" type="ru.javawebinar.topjava.model.MealWithExceed"/>
                <tr style="align-content:center;color:<c:out value="${mealWithExceed.isExceed() == true ? 'red' : 'green'}"/>">
                <td>${mealWithExceed.id}</td>
                    <td>${mealWithExceed.description}</td>
                    <td>${mealWithExceed.calories}</td>
                    <td><%=TimeUtil.toString(mealWithExceed.getDateTime())%></td>
                    <td><a class="btn btn-success" role="button"
                           href="meals?action=update&id=${mealWithExceed.getId()}">Update</a> <a
                            class="btn btn-danger" role="button"
                            href="meals?action=delete&id=${mealWithExceed.getId()}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</div>

</body>
</html>