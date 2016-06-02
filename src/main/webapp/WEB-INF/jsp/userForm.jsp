<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User form</title>
</head>
<body>
    <c:url value="/users" var="actionUrl"/>
    <form:form modelAttribute="user" action="${actionUrl}">
        <form:hidden path="id"/>
        <table>
            <tr>
                <td>First name:</td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td><form:input path="surname"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><form:password path="password"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save Changes"/>
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>
