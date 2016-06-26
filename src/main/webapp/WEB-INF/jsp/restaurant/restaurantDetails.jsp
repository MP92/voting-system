<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2>Restaurant details</h2>
    </div>
    <div class="panel panel-info">
        <div class="panel-heading">
            <h2 class="panel-title"><strong>${restaurant.name}</strong></h2>
        </div>
        <div class="panel-body">
            <table class="table table-user-information">
                <tbody>
                <tr>
                    <th>ID</th>
                    <td>${restaurant.id}</td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>${restaurant.description}</td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td>${restaurant.address}</td>
                </tr>
                <tr>
                    <th>Phone number</th>
                    <td>${restaurant.phoneNumber}</td>
                </tr>
                <c:if test="${not empty restaurant.menu}">
                    <tr>
                        <th>Menu</th>
                        <td>
                            <table class="table table-condensed">
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
                </c:if>
                </tbody>
            </table>
        </div>
        <div class="panel-footer">
            <a class="btn btn-sm btn-info" href="${rootUrl}/dishes?restaurantId=${restaurant.id}">Dish list</a>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
