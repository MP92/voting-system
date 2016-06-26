<%@ page import="ru.pkg.model.DishCategory" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<div class="container">
<form:form modelAttribute="dish" action="save" cssClass="form-horizontal">
    <h2 class="form-signin-heading">Dish form</h2>

    <input type="hidden" name="restaurantId" value="${restaurantId}">
    <form:hidden path="id"/>
    <form:hidden path="inMenu"/>
    <div class="form-group">
        <form:label path="name" cssClass="col-xs-2 control-label">Name</form:label>
        <div class="col-sm-5">
            <form:input path="name" cssClass="form-control"/>
        </div>
        <div class="col-sm-5">
            <form:errors path="name"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="description" cssClass="col-xs-2 control-label">Description</form:label>
        <div class="col-sm-5">
            <form:input path="description" cssClass="form-control"/>
        </div>
        <div class="col-sm-5">
            <form:errors path="description"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="weight" cssClass="col-xs-2 control-label">Weight</form:label>
        <div class="col-sm-5">
            <form:input path="weight" cssClass="form-control"/>
        </div>
        <div class="col-sm-5">
            <form:errors path="weight"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="category" cssClass="col-xs-2 control-label">Category</form:label>
        <div class="col-sm-5">
            <form:select path="category" cssClass="form-control" items="<%= DishCategory.values() %>"/>
        </div>
        <div class="col-sm-5">
            <form:errors path="category"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="price" cssClass="col-xs-2 control-label">Price</form:label>
        <div class="col-sm-5">
            <form:input path="price" cssClass="form-control"/>
        </div>
        <div class="col-sm-5">
            <form:errors path="price"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <input type="submit" value="Save changes"/>
        </div>
    </div>
</form:form>
</div>
</body>
</html>
