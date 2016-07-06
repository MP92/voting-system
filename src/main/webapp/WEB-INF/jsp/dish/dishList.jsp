<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container dish-container">
    <c:set var="restaurantId" value="${param.restaurantId}"/>

    <c:if test="${empty restaurantId}">
        <div class="page-header">
            <h1><spring:message code="restaurant.id.not.specified"/></h1>
        </div>
    </c:if>
    <c:if test="${not empty restaurantId}">
        <c:set var="restaurantAjaxUrl" value="${pageContext.request.contextPath}/ajax/restaurants/"/>

        <div class="page-header">
            <h2><spring:message code="dish.list.header" arguments="${restaurantId}"/></h2>
        </div>
        <div class="margin-bottom">
            <a class="btn btn-primary" onclick="initAddRecord()">Add dish</a>
            <a class="btn btn-info pull-right" onclick="showRestaurantDetails(${restaurantId},'${restaurantAjaxUrl}')"><spring:message code="restaurant.details"/></a>
        </div>
        <table id="datatable" class="table table-striped">
            <thead>
            <tr>
                <th><spring:message code="form.id"/></th>
                <th><spring:message code="form.name"/></th>
                <th><spring:message code="form.description"/></th>
                <th><spring:message code="form.weight"/></th>
                <th><spring:message code="form.category"/></th>
                <th><spring:message code="form.price"/></th>
                <th><spring:message code="form.inMenu"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>

        <jsp:include page="dishForm.jsp" />
        <jsp:include page="/WEB-INF/jsp/restaurant/restaurantDetails.jsp" />
    </c:if>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
<script type="text/javascript" src="${rootUrl}/resources/js/dish.js?n=1"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurant/restaurantDetails.js?n=1"></script>
</body>
</html>
