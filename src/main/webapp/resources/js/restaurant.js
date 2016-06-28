var ajaxUrl = getContextPath() + '/ajax/restaurants/';
var datatableApi;
var detailsInfo;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
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
            {
                "data": "name",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<a onclick="showRestaurantDetails(' + row.id + ');">' + data + '</a>';
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
                        return '<a class="btn btn-xs btn-success" href="restaurants/vote?restaurantId=' + row.id + '">Vote</a>'
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render" : function (data, type, row) {
                    if (type == 'display') {
                        return '<a class="btn btn-xs btn-info" href="dishes?restaurantId=' + row.id + '">Dish list</a>'
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