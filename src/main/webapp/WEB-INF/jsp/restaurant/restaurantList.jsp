<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div style="background-color:blue;">
    <c:if test="${not empty votedId}">
        You has voted for restaurant with id=${votedId}
    </c:if>
</div>
<div style="background-color:green;">
    <c:if test="${not empty status}">
        Restaurant ${status}
    </c:if>
</div>

<h2>Restaurant list</h2>
<a href="restaurants/add">Add</a>
<table border="1" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Address</th>
        <th>Phone number</th>
        <th></th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${restaurantList}" var="restaurant">
        <tr>
            <td>${restaurant.id}</td>
            <td><a href="restaurants/details?id=${restaurant.id}">${restaurant.name}</a></td>
            <td>${restaurant.description}</td>
            <td>${restaurant.address}</td>
            <td>${restaurant.phoneNumber}</td>
            <td><a href="${rootUrl}vote?restaurantId=${restaurant.id}">Vote</a></td>
            <td><a href="restaurants/edit?id=${restaurant.id}">Update</a></td>
            <td><a href="restaurants/delete?id=${restaurant.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<h2>Votes list</h2>
<table border="1" cellpadding="0">
    <thead>
    <tr>
        <th>Restaurant name</th>
        <th>Votes</th>
    </tr>
    </thead>
    <c:forEach items="${restaurantList}" var="restaurant">
        <tr>
            <td>${restaurant.name}</td>
            <td>${restaurant.votes}</td>
        </tr>
    </c:forEach>
</table>
<a href="restaurants/resetVotes">Reset votes</a>
</body>
</html>
