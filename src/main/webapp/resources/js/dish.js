var restaurantId = getQueryVariable("restaurantId");
var ajaxUrl = ctx + "/ajax/restaurants/" + restaurantId + "/dishes/";
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function changeState(chkbox, id) {
    $.ajax({
         url: ajaxUrl + id,
         type: "POST",
         success() {
             var enabled = chkbox.is(":checked");
             setHighlight(chkbox.closest("tr"), enabled);
             var msgCode = enabled ? "dish.menu.added" : "dish.menu.removed";
             showSuccessToast(messages[msgCode]);
         }
    });
}

$(function () {
    if (!restaurantId) {
        return;
    }

    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {"data": "name"},
            {"data": "description"},
            {"data": "weight"},
            {"data": "category"},
            {"data": "price"},
            {
                "data": "inMenu",
                "render"(data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='changeState($(this)," + row.id + ");'/>";
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
        "createdRow"(row, data, dataIndex) {
            if (data.inMenu) {
                $(row).addClass("row-highlight");
            }
        }
    });
});
