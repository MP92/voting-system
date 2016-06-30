var restaurantId = getQueryVariable("restaurantId");
var ajaxUrl = getContextPath() + '/ajax/restaurants/' + restaurantId + '/dishes/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function changeState(chkbox, id) {
    var enabled = chkbox.is(":checked");
    $.ajax({
         url: ajaxUrl + id,
         type: 'POST',
         success: function () {
             alert('state changed');
             var tr = chkbox.closest('tr');
             if (enabled) {
                 tr.addClass('row-highlight');
             }
             else {
                 tr.removeClass('row-highlight');
             }
         }
    });
}

$(function () {
    if (!restaurantId) {
        return;
    }

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
            {"data": "description"},
            {"data": "weight"},
            {"data": "category"},
            {"data": "price"},
            {
                "data": "inMenu",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<input type="checkbox" ' + (data ? 'checked' : '') + ' onclick="changeState($(this),' + row.id + ');"/>';
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
            if (data.inMenu) {
                $(row).addClass('row-highlight')
            }
        }
    });
});
