var ajaxUrl = getContextPath() + '/ajax/admin/users/';
var datatableApi;
var profileInfo;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function showProfile(id) {
    profileInfo.find("td").text("");
    $.get(ajaxUrl + id, function (data) {
        var voteFields = profileInfo.find(".voting-data");
        var voted = false;
        $.each(data, function (key, value) {
            if (key === "userVote" && value.lastVoted) {
                voted = true;
                profileInfo.find(".profile-lastVoted").text(value.lastVoted);
                profileInfo.find(".profile-restaurant").text(value.restaurantId);
            } else {
                profileInfo.find(".profile-" + key).text(value);
            }
        });
        voted ? voteFields.show() : voteFields.hide();
        $('#profile').modal();
    });
}

function renderProfileBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-info" onclick="showProfile(' + row.id + ');">Profile</a>';
    }
    return data;
}

function enable(chkbox, id) {
    $.ajax({
         url: ajaxUrl + id,
         type: 'POST',
         success: function () {
             var enabled = chkbox.is(":checked");
             chkbox.closest('tr').css("text-decoration", enabled ? "none" : "line-through");
             showSuccessToast('User ' + enabled ? 'enabled' : 'disabled');
         }
    });
}

$(function () {
    profileInfo = $('#profileInfo');

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
                        return '<a class="btn btn-xs btn-info" onclick="showProfile(' + row.id + ');">Profile</a>';
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