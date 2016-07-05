<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<link rel="stylesheet" href="${rootUrl}/resources/css/restaurantCatalog.css"/>
<link rel="stylesheet" href="${rootUrl}/resources/css/voting.css"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2><spring:message code="restaurant.catalog"/></h2>
    </div>
    <div class="row">
        <div class="col-sm-offset-9 col-sm-3 sidebar">
            <div class="page-header">
                <h3 class="text-center"><spring:message code="voting.statistics"/></h3>
            </div>
            <div class="voting-chart">
            </div>
            <div class="chart-footer text-center">
                <a class="voting-button btn-cancel"><spring:message code="voting.cancel"/></a>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="voting-button btn-reset" onclick="resetVotes()"><spring:message code="voting.reset"/></a>
                </sec:authorize>
            </div>
        </div>
        <div class="restaurant-catalog col-sm-9">
            <div class="catalog-items clearfix">
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/restaurant/restaurantDetails.jsp" />
</body>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>

<script type="text/javascript" src="${rootUrl}/resources/js/utils/templatesUtils.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurant/restaurantCatalog.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurant/restaurantDetails.js"></script>
</html>
