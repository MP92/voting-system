<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <c:if test="${not empty message}">
        <div class="alert alert-warning" role="alert">
            ${message}
        </div>
    </c:if>
    <div class="page-header">
        <h2>User list</h2>
    </div>
    <a class="btn btn-primary" href="users/add">Add user</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Registered</th>
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
                <td>${user.enabled}</td>
                <td>${user.roles}</td>
                <td><a class="btn btn-xs btn-warning" href="users/edit?id=${user.id}">update</a></td>
                <td><a class="btn btn-xs btn-danger" href="users/delete?id=${user.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
