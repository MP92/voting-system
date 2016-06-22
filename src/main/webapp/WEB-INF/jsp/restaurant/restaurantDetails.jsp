<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<h2>Restaurant details</h2>
<table border="1" cellspacing="0">
    <tr>
        <td>ID</td>
        <td>${restaurant.id}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${restaurant.name}</td>
    </tr>
    <tr>
        <td>Description</td>
        <td>${restaurant.description}</td>
    </tr>
    <tr>
        <td>Address</td>
        <td>${restaurant.address}</td>
    </tr>
    <tr>
        <td>Phone number</td>
        <td>${restaurant.phoneNumber}</td>
    </tr>
    <tr>
        <td>Menu</td>
        <td>
            <table border="1" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Weight</th>
                    <th>Category</th>
                    <th>Price</th>
                </tr>
                <c:forEach items="${restaurant.menu}" var="dish">
                    <tr>
                        <td>${dish.id}</td>
                        <td>${dish.name}</td>
                        <td>${dish.description}</td>
                        <td>${dish.weight}</td>
                        <td>${dish.category}</td>
                        <td>${dish.price}</td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<a href="${rootUrl}/dishes?restaurantId=${restaurant.id}">Dish list</a>
</body>
</html>
