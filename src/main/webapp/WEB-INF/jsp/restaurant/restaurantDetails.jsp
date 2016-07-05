<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="modal fade" id="details">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h2 class="modal-title"><spring:message code="restaurant.details"/></h2>
            </div>
            <div class="modal-body">
                <div class="panel panel-info" id="detailsInfo">
                    <div class="panel-heading">
                    </div>
                    <div class="panel-body">
                        <table class="table table-user-information">
                            <tbody>
                            <tr>
                                <th><spring:message code="form.id"/></th>
                                <td class="details-id"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.name"/></th>
                                <td class="details-name"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.description"/></th>
                                <td class="details-description"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.address"/></th>
                                <td class="details-address"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.phoneNumber"/></th>
                                <td class="details-phoneNumber"></td>
                            </tr>
                            <tr class="menu">
                                <th><spring:message code="form.menu"/></th>
                                <td class="details-menu">
                                    <table id="menuDatatable" class="table table-condensed">
                                        <tr>
                                            <th><spring:message code="form.id"/></th>
                                            <th><spring:message code="form.name"/></th>
                                            <th><spring:message code="form.weight"/></th>
                                            <th><spring:message code="form.category"/></th>
                                            <th><spring:message code="form.price"/></th>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>