<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="modal fade" id="details">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h2 class="modal-title"><spring:message code="user.details"/></h2>
            </div>
            <div class="modal-body">
                <div class="panel panel-info" id="detailsInfo">
                    <div class="panel-heading">
                    </div>
                    <div class="panel-body">
                        <table class="table table-user-information">
                            <tbody>
                            <tr>
                                <th><spring:message code="form.name"/></th>
                                <td class="details-name"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.surname"/></th>
                                <td class="details-surname"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.registered"/></th>
                                <td class="details-registered"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.enabled"/></th>
                                <td class="details-enabled"></td>
                            </tr>
                            <tr>
                                <th><spring:message code="form.authorities"/></th>
                                <td class="details-roles"></td>
                            </tr>
                            <tr class="voting-data">
                                <th><spring:message code="form.lastVoted"/></th>
                                <td class="details-lastVoted"></td>
                            </tr>
                            <tr class="voting-data">
                                <th><spring:message code="form.lastRestaurant"/></th>
                                <td class="details-restaurant"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>