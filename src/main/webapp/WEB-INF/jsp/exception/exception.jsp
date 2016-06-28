<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2>Application error:</h2>
    </div>
    <h2>${exception.message}</h2>
    <!--
    <c:forEach items="${exception.stackTrace}" var="stackTrace">
        ${stackTrace}
    </c:forEach>
    -->
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
