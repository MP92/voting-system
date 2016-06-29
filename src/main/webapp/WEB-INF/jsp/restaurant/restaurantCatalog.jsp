<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<link rel="stylesheet" href="${rootUrl}/resources/css/restaurantCatalog.css"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2>Restaurant catalog</h2>
    </div>
    <div class="row">
        <div class="col-sm-offset-9 col-sm-3 sidebar">
            <p>Voting statistics should be here</p>
        </div>
        <div class="restaurant-catalog col-sm-9">
            <div class="catalog-items clearfix">
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/restaurant/restaurantDetails.jsp" %>
</body>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>

<c:set var="restaurantAjaxUrl" value="${pageContext.request.contextPath}/ajax/restaurants/"/>
<script id="item-template" type="text/x-handlebars-template">
    <div class="catalog-item">
        <div class="item-header"><strong class="item-header-text">{{name}}</strong></div>
        <div class="item-img">
            <img src="${rootUrl}/resources/img/catalog-img.jpg" alt="Изображение макета">
            <div class="item-card">
                <h3 class="item-title">{{name}}</h3>
                <p class="item-text item-address">{{address}}</p>
                <p class="item-text item-phoneNumber">{{phoneNumber}}</p>
                <a onclick="showRestaurantDetails('{{id}}', '${restaurantAjaxUrl}')" class="item-btn">Show details</a>
            </div>
        </div>
    </div>
</script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurantCatalog.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/menu.js"></script>
</html>
