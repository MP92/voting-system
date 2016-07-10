var ajaxUrl = ctx + "/ajax/restaurants/";
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name",
                "render"(data, type, row) {
                    if (type === "display") {
                        return "<a style='cursor: pointer' onclick='showRestaurantDetails(" + row.id + ");'>" + data + "</a>";
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
                "render"(data, type, row) {
                    if (type === "display") {
                        var uri = "/dishes?restaurantId={0}&restaurantName={1}".format(row.id, row.name); 
                        return "<a class='btn btn-xs btn-info' href='" + ctx + uri + "'>" +
                            messages["dish.list"] + "</a>";
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