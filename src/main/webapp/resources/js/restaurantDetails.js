var detailsInfo;
var menuTableApi;

$(function() {
    detailsInfo = $('#detailsInfo');

    menuTableApi = $('#menuDatatable').DataTable({
        "data": {"id": "", "name": "", "weight": "", "category": "", "price": ""},
        "paging": false,
        "info": false,
        "filter": false,
        "columns": [
            {"data": "id"},
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

function showRestaurantDetails(id, url) {
    if (typeof url === 'undefined') {
        url = getContextPath() + '/ajax/restaurants/';
    }
    $.get(url + id, function (data) {
        detailsInfo.find("td:not(.details-menu)").text("");
        $.each(data, function (key, value) {
            if (key === 'menu') {
                var tr = detailsInfo.find(".menu");
                if (value && value.length > 0) {
                    tr.show();
                    updateMenuTableByData(value, menuTableApi);
                } else {
                    tr.hide();
                }
            } else {
                detailsInfo.find(".details-" + key).text(value);
            }
        });
        $('#details').modal();
    });
}

function updateMenuTableByData(data) {
    menuTableApi.clear().rows.add(data).draw();
}
