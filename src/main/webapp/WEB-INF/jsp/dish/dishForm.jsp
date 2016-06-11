<%@ page import="ru.pkg.model.DishCategory" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<form:form modelAttribute="dish" action="save">
    <table>
        <form:hidden path="id"/>
        <form:hidden path="restaurantId"/>
        <tr>
            <th><form:label path="name">Name</form:label></th>
            <td><form:input path="name"/></td>
            <td><form:errors path="name"/></td>
        </tr>
        <tr>
            <th><form:label path="description">Description</form:label></th>
            <td><form:input path="description"/></td>
            <td><form:errors path="description"/></td>
        </tr>
        <tr>
            <th><form:label path="weight">Weight</form:label></th>
            <td><form:input path="weight"/></td>
            <td><form:errors path="weight"/></td>
        </tr>
        <tr>
            <th><form:label path="category">Category</form:label></th>
            <td><form:select path="category" items="<%= DishCategory.values() %>"/></td>
            <td><form:errors path="category"/></td>
        </tr>
        <tr>
            <th><form:label path="price">Price</form:label></th>
            <td><form:input path="price"/></td>
            <td><form:errors path="price"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Save changes"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
