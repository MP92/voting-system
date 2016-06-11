<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<h2>Restaurant form</h2>
<form:form modelAttribute="restaurant" action="save">
    <table>
        <form:hidden path="id"/>
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
            <th><form:label path="address">Address</form:label></th>
            <td><form:input path="address"/></td>
            <td><form:errors path="address"/></td>
        </tr>
        <tr>
            <th><form:label path="phoneNumber">Phone number</form:label></th>
            <td><form:input path="phoneNumber"/></td>
            <td><form:errors path="phoneNumber"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Save Changes"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
