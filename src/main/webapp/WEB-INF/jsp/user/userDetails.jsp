<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<h2>Profile details</h2>
<table>
    <tr>
        <th>ID</th>
        <td>${user.id}</td>
    </tr>
    <tr>
        <th>Name</th>
        <td>${user.name}</td>
    </tr>
    <tr>
        <th>Surname</th>
        <td>${user.surname}</td>
    </tr>
    <tr>
        <th>Registered</th>
        <td>${user.registered}</td>
    </tr>
    <tr>
        <th>Last Voted</th>
        <td>${user.lastVoted}</td>
    </tr>
    <tr>
        <th>Enabled</th>
        <td>${user.enabled}</td>
    </tr>
    <tr>
        <th>Roles</th>
        <td>${user.roles}</td>
    </tr>
</table>

<a href="${rootUrl}/admin/users/edit?id=${user.id}"></a>

</body>
</html>
