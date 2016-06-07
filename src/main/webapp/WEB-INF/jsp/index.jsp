<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />
<h1>Welcome</h1>
<form method="post" action="login">
    <label>
        Select user:
        <select name="userName">
            <option>admin</option>
            <option>user</option>
        </select>
    </label>
    <input type="submit" value="Select"/>
</form>
</body>
</html>
