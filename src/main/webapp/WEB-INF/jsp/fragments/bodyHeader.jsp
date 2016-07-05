<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="<c:url value='/'/>" class="navbar-brand"><spring:message code="app.title"/></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a href="${rootUrl}/users"><spring:message code="user.list"/></a></li>
                        <li><a href="${rootUrl}/admin/restaurants"><spring:message code="restaurant.table"/></a></li>
                    </sec:authorize>
                    <li><a href="${rootUrl}/restaurants"><spring:message code="restaurant.catalog"/></a></li>
                </ul>
            </sec:authorize>
            <jsp:include page="lang.jsp"/>
            <sec:authorize access="isAuthenticated()">
                <form:form class="navbar-form navbar-right" action="logout" method="post">
                    <a class="btn btn-info" href="${rootUrl}/profile"><spring:message code="profile"/></a>
                    <input type="submit" class="btn btn-primary" value="<spring:message code="logout"/>">
                </form:form>
            </sec:authorize>
        </div>
    </div>
</nav>
