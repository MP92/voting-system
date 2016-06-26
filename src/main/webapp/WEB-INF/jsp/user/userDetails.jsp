<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<div class="container">
    <div class="page-header">
        <h2>User profile</h2>
    </div>
    <div class="panel panel-info">
        <div class="panel-heading">
            <h2 class="panel-title"><strong>${user.name} ${user.surname}</strong></h2>
        </div>
        <div class="panel-body">
            <table class="table table-user-information">
                <tbody>
                <tr>
                    <th>ID</th>
                    <td>${user.id}</td>
                </tr>
                <tr>
                    <th>Registered</th>
                    <td>${user.registered}</td>
                </tr>
                <tr>
                    <th>Last voted</th>
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
                </tbody>
            </table>
        </div>
        <div class="panel-footer">
            <a href="${rootUrl}/profile/edit" type="button" class="btn btn-sm btn-warning"
               title="Edit profile" data-toggle="tooltip">
                <i class="glyphicon glyphicon-edit"></i>
            </a>
        </div>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</html>
