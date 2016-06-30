<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp" />
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp" />

<div class="container">
    <div class="page-header">
        <h2>Restaurant list</h2>
    </div>
    <a class="btn btn-primary" onclick="initAddRecord()">Add restaurant</a>
    <table class="table table-striped" id="datatable">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Address</th>
            <th>Phone number</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
    </table>

    <%@ include file="restaurantForm.jsp" %>
    <%@ include file="restaurantDetails.jsp" %>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurantTable.js"></script>
<script type="text/javascript" src="${rootUrl}/resources/js/restaurantDetails.js"></script>
</body>
</html>
