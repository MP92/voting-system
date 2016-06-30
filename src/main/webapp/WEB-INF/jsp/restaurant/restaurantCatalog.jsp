<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<link rel="stylesheet" href="${rootUrl}/resources/css/restaurantCatalog.css"/>
<link rel="stylesheet" href="${rootUrl}/resources/css/votingChart.css"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2>Restaurant catalog</h2>
    </div>
    <div class="row">
        <div class="col-sm-offset-9 col-sm-3 sidebar">
            <div class="page-header">
                <h3 class="text-center">Voting statistics</h3>
            </div>
            <div class="voting-chart">
            </div>
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

<script type="text/javascript" src="${rootUrl}/resources/js/templates.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurantCatalog.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/votingChart.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurantDetails.js"></script>
</html>
