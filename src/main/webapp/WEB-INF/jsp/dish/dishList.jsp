<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <c:if test="${dishList != null}">
        <div class="page-header">
            <h2>Dish list for restaurant with id=${restaurantId}</h2>
        </div>
        <a class="btn btn-primary" href="dishes/add?restaurantId=${restaurantId}">Add dish</a>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Weight</th>
                <th>Category</th>
                <th>Price</th>
                <th>In Menu?</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${dishList}" var="dish">
                <tr>
                    <td>${dish.id}</td>
                    <td>${dish.name}</td>
                    <td>${dish.description}</td>
                    <td>${dish.weight}</td>
                    <td>${dish.category}</td>
                    <td>${dish.price}</td>
                    <td class="text-nowrap">${dish.inMenu}
                        <a href="dishes/menuState?id=${dish.id}&restaurantId=${restaurantId}">
                            <i class="glyphicon glyphicon-refresh"></i>
                        </a>
                    </td>
                    <td><a class="btn btn-xs btn-warning" href="dishes/edit?id=${dish.id}&restaurantId=${restaurantId}">Edit</a></td>
                    <td><a class="btn btn-xs btn-danger" href="dishes/delete?id=${dish.id}&restaurantId=${restaurantId}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
        <a class="btn btn-sm btn-info" href="${rootUrl}/restaurants/details?id=${restaurantId}">Restaurant details</a>
    </c:if>
    <form method="get" class="form-inline indent-top">
        <div class="form-group">
            <label for="restaurantId">Select restaurant id:</label>
            <select id="restaurantId" name="restaurantId" class="form-control">
                <c:forEach items="${restaurantIDs}" var="restaurantID">
                    <option>${restaurantID}</option>
                </c:forEach>
            </select>
        </div>
        <input class="btn btn-default" type="submit" value="Submit"/>
    </form>
</div>


<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
