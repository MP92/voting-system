var detailsInfo;
var menuTableApi;
var menuRow;

$(function() {
    detailsInfo = $("#detailsInfo");
    menuRow = detailsInfo.find(".menu");

    menuTableApi = $("#menuDatatable").DataTable({
        "data": {"name": "", "weight": "", "category": "", "price": ""},
        "paging": false,
        "info": false,
        "filter": false,
        "columns": [
            {"data": "name"},
            {"data": "weight"},
            {"data": "category"},
            {"data": "price"}
        ],
        "order": [
            [0, "asc"]
        ]
    });
});

function updateMenuTableByData(data) {
    menuTableApi.clear().rows.add(data).draw();
}

function showRestaurantDetails(id, url) {
    if (typeof url === "undefined") {
        url = ctx + "/ajax/restaurants/";
    }
    $.get(url + id, function (data) {
        detailsInfo.find("td:not(.details-menu)").text("");
        var menuFound = false;
        $.each(data, function (key, value) {
            if (key === "menu") {
                menuFound = true;
                if (value && value.length > 0) {
                    menuRow.show();
                    updateMenuTableByData(value, menuTableApi);
                } else {
                    menuRow.hide();
                }
            } else {
                detailsInfo.find(".details-" + key).text(value);
            }
        });
        if (!menuFound) {
            menuRow.hide();
        }
        $("#details").modal();
    });
}


