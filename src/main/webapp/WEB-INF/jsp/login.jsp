<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="<c:url value='/'/>" class="navbar-brand">Voting system</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <form:form class="navbar-form navbar-right" action="spring_security_check" method="post">
                <div class="form-group">
                    <input type="text" placeholder="Name" class="form-control" name='username'>
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Password" class="form-control" name='password'>
                </div>
                <button type="submit" class="btn btn-success">Login</button>
            </form:form>
        </div>
    </div>
</nav>

<div class="container">
    <c:if test="${error}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh snap!</strong>
            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success" role="alert">
            <strong>Success!</strong>
            ${message}
        </div>
    </c:if>
    <div class="row">
        <div class="col-md-5">
            <div class="page-header">
                <h2>Welcome</h2>
            </div>
            <a class="btn btn-primary btn-lg" href="register">Register &raquo;</a>
        </div>
        <div class="col-md-offset-2 col-md-5">
            <div class="jumbotron">
                <table class="table">
                    <caption class="text-info lead">Default user accounts:</caption>
                    <thead>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Admin</td>
                        <td>admin</td>
                    </tr>
                    <tr>
                        <td>User</td>
                        <td>password</td>
                    </tr>
                    <tr>
                        <td>User2</td>
                        <td>password2</td>
                    </tr>
                    <tr>
                        <td>User3</td>
                        <td>password3</td>
                    </tr>
                    <tr>
                        <td>User4</td>
                        <td>password4</td>
                    </tr>
                    <tr>
                        <td>User5</td>
                        <td>password5</td>
                    </tr>
                    <tr>
                        <td>User6</td>
                        <td>password6</td>
                    </tr>
                    <tr>
                        <td>User7</td>
                        <td>password7</td>
                    </tr>
                    <tr>
                        <td>User8</td>
                        <td>password8</td>
                    </tr>
                    <tr>
                        <td>User9</td>
                        <td>password9</td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>
</body>
</html>
