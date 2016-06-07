<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div style="background-color:green;">
    <c:if test="${not empty status}">
        Dish for restaurant with id=${restaurantId} ${status}
    </c:if>
</div>
<c:if test="${not empty dishList}">
    <h2>Dish list for restaurant with id=${restaurantId}</h2>
    <a href="dishes/add?restaurantId=${restaurantId}">Add</a>
    <table border="1" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Weight</th>
            <th>Category</th>
            <th>Price</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${dishList}" var="dish">
            <tr>
                <td>${dish.id}</td>
                <td>${dish.name}</td>
                <td>${dish.description}</td>
                <td>${dish.weight}</td>
                <td>${dish.category}</td>
                <td>${dish.price}</td>
                <td><a href="dishes/edit?id=${dish.id}&restaurantId=${restaurantId}">Edit</a></td>
                <td><a href="dishes/delete?id=${dish.id}&restaurantId=${restaurantId}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="${rootUrl}restaurants/details?id=${restaurantId}">Restaurant details</a>
</c:if>
<form method="get">
    <label for="restaurantId">Select restaurant id: </label>
    <select id="restaurantId" name="restaurantId">
        <c:forEach items="${restaurantIDs}" var="restaurantID">
            <option>${restaurantID}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
