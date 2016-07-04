<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="${rootUrl}" class="navbar-brand">Voting system</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a href="${rootUrl}/users">User list</a></li>
                        <li><a href="${rootUrl}/admin/restaurants">Restaurant table</a></li>
                    </sec:authorize>
                    <li><a href="${rootUrl}/restaurants">Restaurant catalog</a></li>
                </ul>
                <form:form class="navbar-form navbar-right" action="logout" method="post">
                    <a class="btn btn-info" href="${rootUrl}/profile">Profile</a>
                    <input type="submit" class="btn btn-primary" value="Logout">
                </form:form>
            </sec:authorize>
        </div>
    </div>
</nav>
