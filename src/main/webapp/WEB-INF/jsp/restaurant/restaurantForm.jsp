<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<div class="container">
<form:form modelAttribute="restaurant" action="save" cssClass="form-horizontal">
    <h2 class="form-signin-heading">Restaurant form</h2>

    <form:hidden path="id"/>
    <div class="form-group">
        <form:label path="name" cssClass="col-xs-2 control-label">Name</form:label>
        <div class="col-xs-5">
            <form:input path="name" cssClass="form-control"/>
        </div>
        <div class="col-xs-5">
            <form:errors path="name"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="description" cssClass="col-xs-2 control-label">Description</form:label>
        <div class="col-xs-5">
            <form:input path="description" cssClass="form-control"/>
        </div>
        <div class="col-xs-5">
            <form:errors path="description"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="address" cssClass="col-xs-2 control-label">Address</form:label>
        <div class="col-xs-5">
            <form:input path="address" cssClass="form-control"/>
        </div>
        <div class="col-xs-5">
            <form:errors path="address"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="phoneNumber" cssClass="col-xs-2 control-label">Phone number</form:label>
        <div class="col-xs-5">
            <form:input path="phoneNumber" cssClass="form-control"/>
        </div>
        <div class="col-xs-5">
            <form:errors path="phoneNumber"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <input type="submit" class="btn btn-default" value="Save Changes"/>
        </div>
    </div>
</form:form>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
