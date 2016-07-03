<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2>User list</h2>
    </div>
    <a class="btn btn-primary" onclick="initAddRecord()">Add user</a>
    <table class="table table-striped" id="datatable">
        <thead>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Registered</th>
            <th>Enabled</th>
            <th>Roles</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
    </table>

    <%@ include file="userForm.jsp" %>
    <%@ include file="userDetails.jsp" %>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
<script type="text/javascript" src="${rootUrl}/resources/js/user/user.js"></script>
</body>
</html>
