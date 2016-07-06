var ajaxUrl = getContextPath() + '/ajax/users/';
var datatableApi;
var detailsInfo;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function showUserDetails(id) {
    detailsInfo.find("td").text("");
    $.get(ajaxUrl + id, function (data) {
        var voteFields = detailsInfo.find(".voting-data");
        var voted = false;
        $.each(data, function (key, value) {
            if (key === "userVote" && value.lastVoted) {
                voted = true;
                detailsInfo.find(".details-lastVoted").text(value.lastVoted);
                detailsInfo.find(".details-restaurant").text(value.restaurantId);
            } else {
                detailsInfo.find(".details-" + key).text(value);
            }
        });
        voted ? voteFields.show() : voteFields.hide();
        $('#details').modal();
    });
}

function enable(chkbox, id) {
    $.ajax({
         url: ajaxUrl + id,
         type: 'POST',
         success: function () {
             var enabled = chkbox.is(":checked");
             chkbox.closest('tr').css("text-decoration", enabled ? "none" : "line-through");
             var msgCode = (enabled ? 'user.enabled' : 'user.disabled');
             showSuccessToast(messages[msgCode]);
         }
    });
}

$(function () {
    detailsInfo = $('#detailsInfo');

    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {"data": "id"},
            {"data": "name"},
            {"data": "surname"},
            {
                "data": "registered",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        var dateObject = new Date(date);
                        return '<span>' + dateObject.toISOString().substring(0, 10) + '</span>';
                    }
                    return date;
                }
            },
            {
                "data": "enabled",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<input type="checkbox" ' + (data ? 'checked' : '') + ' onclick="enable($(this),' + row.id + ');"/>';
                    }
                    return data;
                }
            },
            {"data": "roles"},
            {
                "orderable": false,
                "defaultContent": "",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<a class="btn btn-xs btn-info" onclick="showUserDetails(' + row.id + ');">' + 
                            messages['details'] + '</a>';
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [0, "asc"]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (!data.enabled) {
                $(row).css("text-decoration", "line-through");
            }
        }
    });
});