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
        <h2>Restaurant list</h2>
    </div>
    <a class="btn btn-primary" href="restaurants/add">Add restaurant</a>
    <table class="table table-striped">
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
                <td class="text-nowrap"><a href="restaurants/details?id=${restaurant.id}">${restaurant.name}</a></td>
                <td>${restaurant.description}</td>
                <td>${restaurant.address}</td>
                <td>${restaurant.phoneNumber}</td>
                <td><a class="btn btn-xs btn-success" href="restaurants/vote?restaurantId=${restaurant.id}">Vote</a></td>
                <td><a class="btn btn-xs btn-warning" href="restaurants/edit?id=${restaurant.id}">Update</a></td>
                <td><a class="btn btn-xs btn-danger" href="restaurants/delete?id=${restaurant.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

    <h2>Votes list</h2>
    <table border="1" cellpadding="0">
        <thead>
        <tr>
            <th>Restaurant ID</th>
            <th>Restaurant name</th>
            <th>Votes</th>
            <th>Percentage</th>
        </tr>
        </thead>
        <c:forEach items="${votingStatistics}" var="item">
            <tr>
                <td>${item.restaurantId}</td>
                <td>${item.restaurantName}</td>
                <td>${item.votes}</td>
                <td>${item.percentage}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="restaurants/resetVotes">Reset votes</a>
    <a href="restaurants/cancelVote">Cancel vote</a>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
