<%@ page import="ru.pkg.model.DishCategory" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title">Dish form</h2>
            </div>
            <div class="modal-body">
                <form:form method="post" cssClass="form-horizontal" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3">Name</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="weight" class="control-label col-xs-3">Weight</label>

                        <div class="col-xs-9">
                            <input type="number" step="1" class="form-control" id="weight" name="weight" placeholder="Weight">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="category" class="control-label col-xs-3">Category</label>

                        <div class="col-xs-9">
                            <select class="form-control" id="category" name="category">
                                <c:forEach items="<%= DishCategory.values() %>" var="item">
                                    <option value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="control-label col-xs-3">Price</label>

                        <div class="col-xs-9">
                            <input type="number" step="0.01" class="form-control" id="price" name="price" placeholder="Price">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="inMenu" class="control-label col-xs-3">Dish in menu?</label>

                        <div class="col-xs-9">
                            <input type="checkbox" id="inMenu" name="inMenu" value="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
