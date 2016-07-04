<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Voting system</title>
    <c:set var="rootUrl" value="${pageContext.request.contextPath}" scope="application"/>

    <link rel="stylesheet" href="${rootUrl}/webjars/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="${rootUrl}/webjars/datatables/1.10.11/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="${rootUrl}/resources/css/style.css"/>
    <link rel="stylesheet" href="${rootUrl}/webjars/toastr/2.1.2/build/toastr.min.css"/>

    <link rel="shortcut icon" href="${rootUrl}/resources/img/favicon.ico">

    <sec:csrfMetaTags />
</head>
