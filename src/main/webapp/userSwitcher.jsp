<%@ page contentType="text/html;charset=UTF-8" language="java" %>
Current user ID : <c:out value="${cUser}"/>
<form id="userList" name="usrList" action="/meals">
    <section>
        <select name="user" id="selectUsers">
            <c:forEach items="${users}" var="user">
                <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                <option value="${user.id}"
                        <c:if test="${cUser== user.id}">
                            selected
                        </c:if>
                        id="${user.id}">${user.name}</option>
            </c:forEach>
        </select>
        <button id="submit" type="submit">Change user
        </button>
    </section>
</form>