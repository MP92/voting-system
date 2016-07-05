<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2><spring:message code="user.list"/></h2>
    </div>
    <a class="btn btn-primary" onclick="initAddRecord()"><spring:message code="user.add"/></a>
    <table class="table table-striped" id="datatable">
        <thead>
        <tr>
            <th><spring:message code="form.id"/></th>
            <th><spring:message code="form.name"/></th>
            <th><spring:message code="form.surname"/></th>
            <th><spring:message code="form.registered"/></th>
            <th><spring:message code="form.enabled"/></th>
            <th><spring:message code="form.authorities"/></th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
    </table>

    <jsp:include page="userForm.jsp" />
    <jsp:include page="userDetails.jsp" />
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
<script type="text/javascript" src="${rootUrl}/resources/js/user/user.js"></script>
</body>
</html>
