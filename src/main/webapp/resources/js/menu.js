var menuTableApi;

$(function() {
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

function updateMenuTableByData(data) {
    menuTableApi.clear().rows.add(data).draw();
}
