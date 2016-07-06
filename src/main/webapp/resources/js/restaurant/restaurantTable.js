var ajaxUrl = getContextPath() + '/ajax/restaurants/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {"data": "id"},
            {
                "data": "name",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<a style="cursor: pointer" onclick="showRestaurantDetails(' + row.id + ');">' + data + '</a>';
                    }
                    return data;
                }
            },
            {"data": "description"},
            {"data": "address"},
            {"data": "phoneNumber"},
            {
                "orderable": false,
                "defaultContent": "",
                "render" : function (data, type, row) {
                    if (type == 'display') {
                        return '<a class="btn btn-xs btn-info" href="' + getContextPath() + '/dishes?restaurantId=' + row.id + '">' +
                            messages['dish.list'] + '</a>';
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
        ]
    });
});