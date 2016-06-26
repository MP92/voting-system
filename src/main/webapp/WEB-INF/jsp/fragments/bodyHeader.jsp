<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="${rootUrl}" class="navbar-brand">Voting system</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${rootUrl}/admin/users">User list</a></li>
                <li><a href="${rootUrl}/restaurants">Restaurant list</a></li>
                <li><a href="${rootUrl}/dishes">Dish list</a></li>
            </ul>
        </div>
    </div>
</nav>
