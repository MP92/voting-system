<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="${rootUrl}" class="navbar-brand">Voting system</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${rootUrl}/users">User list</a></li>
                <li><a href="${rootUrl}/admin/restaurants">Restaurant table</a></li>
                <li><a href="${rootUrl}/restaurants">Restaurant catalog</a></li>
            </ul>
            <div class="navbar-form navbar-right">
                <a class="btn btn-info" href="${rootUrl}/profile">Profile</a>
                <a class="btn btn-primary" href="${rootUrl}/logout">Logout</a>
            </div>
        </div>
    </div>
</nav>
