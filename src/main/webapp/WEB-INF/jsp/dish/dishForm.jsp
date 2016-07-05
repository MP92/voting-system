<%@ page import="ru.pkg.model.DishCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="form.dish.title"/></h2>
            </div>
            <div class="modal-body">
                <form:form method="post" cssClass="form-horizontal" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><spring:message code="form.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="form.name"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message code="form.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="<spring:message code="form.description"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="weight" class="control-label col-xs-3"><spring:message code="form.weight"/></label>

                        <div class="col-xs-9">
                            <input type="number" step="1" class="form-control" id="weight" name="weight" placeholder="<spring:message code="form.weight"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="category" class="control-label col-xs-3"><spring:message code="form.category"/></label>

                        <div class="col-xs-9">
                            <select class="form-control" id="category" name="category">
                                <c:forEach items="<%= DishCategory.values() %>" var="item">
                                    <option value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="control-label col-xs-3"><spring:message code="form.price"/></label>

                        <div class="col-xs-9">
                            <input type="number" step="0.01" class="form-control" id="price" name="price" placeholder="<spring:message code="form.price"/>">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="inMenu" class="control-label col-xs-3"><spring:message code="form.inMenu"/></label>

                        <div class="col-xs-9">
                            <input type="checkbox" id="inMenu" name="inMenu" value="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary"><spring:message code="form.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>