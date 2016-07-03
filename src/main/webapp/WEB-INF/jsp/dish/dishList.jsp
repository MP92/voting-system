<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container dish-container">
    <c:set var="restaurantId" value="${param.restaurantId}"/>

    <c:if test="${empty restaurantId}">
        <div class="page-header">
            <h1>Restaurant id not specified</h1>
        </div>
    </c:if>
    <c:if test="${not empty restaurantId}">
        <c:set var="restaurantAjaxUrl" value="${pageContext.request.contextPath}/ajax/restaurants/"/>

        <div class="page-header">
            <h2>Dish list for restaurant with id=${restaurantId}</h2>
        </div>
        <div class="margin-bottom">
            <a class="btn btn-primary" onclick="initAddRecord()">Add dish</a>
            <a class="btn btn-info pull-right" onclick="showRestaurantDetails(${restaurantId},'${restaurantAjaxUrl}')">Restaurant details</a>
        </div>
        <table id="datatable" class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Weight</th>
                <th>Category</th>
                <th>Price</th>
                <th>In Menu</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>

        <%@ include file="dishForm.jsp" %>
        <%@ include file="/WEB-INF/jsp/restaurant/restaurantDetails.jsp" %>
    </c:if>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
<script type="text/javascript" src="${rootUrl}/resources/js/dish.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurant/restaurantDetails.js"></script>
</body>
</html>
