<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <form:form modelAttribute="user" action="${user.id != null ? 'profile' : 'register'}" cssClass="form-horizontal">
        <c:url var="headerCode" value="${user.id != null ? 'form.profile.title' : 'form.register.title'}"/>
        <div class="page-header">
            <h2 class="form-signin-heading"><spring:message code="${headerCode}"/></h2>
        </div>

        <div class="form-group">
            <form:label path="name" cssClass="col-xs-2 control-label"><spring:message code="form.name"/></form:label>
            <div class="col-xs-5">
                <form:input path="name" cssClass="form-control"/>
            </div>
            <div class="col-xs-5">
                <form:errors path="name"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="surname" cssClass="col-xs-2 control-label"><spring:message code="form.surname"/></form:label>
            <div class="col-xs-5">
                <form:input path="surname" cssClass="form-control"/>
            </div>
            <div class="col-xs-5">
                <form:errors path="surname"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="password" cssClass="col-xs-2 control-label"><spring:message code="form.password"/></form:label>
            <div class="col-xs-5">
                <form:password path="password" cssClass="form-control"/>
            </div>
            <div class="col-xs-5">
                <form:errors path="password"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-offset-2 col-xs-10">
                <input type="submit" class="btn btn-default" value="<spring:message code="form.save"/>"/>
            </div>
        </div>
    </form:form>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>