<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<c:if test="${not empty message}">
    <div style="background-color:green;">
        ${message}
    </div>
</c:if>

<h2>User list</h2>
<a href="users/add">Add</a>
<table border="1" cellspacing="0">
    <thead>
        <tr>
            <th>Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Registered</th>
            <th>Last Voted</th>
            <th>Enabled</th>
            <th>Roles</th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.registered}</td>
            <td>${user.lastVoted}</td>
            <td>${user.enabled}</td>
            <td>${user.roles}</td>
            <td><a href="users/edit?id=${user.id}">update</a></td>
            <td><a href="users/delete?id=${user.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
