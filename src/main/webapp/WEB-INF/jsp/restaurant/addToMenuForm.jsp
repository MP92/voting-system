<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<h2>Menu form</h2>
<form:form modelAttribute="holder" action="update">
    <form:hidden path="restaurantId"/>
    <table border="1" cellspacing="0">
        <tr>
            <th></th>
            <th>Name</th>
            <th>Description</th>
            <th>Weight</th>
            <th>Category</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${dishList}" var="dish">
            <tr>
                <td><form:checkbox path="dishIDs" value="${dish.id}"/></td>
                <td>${dish.name}</td>
                <td>${dish.description}</td>
                <td>${dish.weight}</td>
                <td>${dish.category}</td>
                <td>${dish.price}</td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Apply"/>
</form:form>
</body>
</html>
