var form;

$(function () {
    form = $('#detailsForm');

    form.submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        alert(jqXHR.responseText);
    });
});

function initAddRecord() {
    form.find(":input:not([type='checkbox'])").val("");
    $('#editRow').modal();
}

function initUpdateRecord(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function deleteRecord(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            alert(id + ' deleted');
        }
    });
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            alert('saved');
        }
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-warning" onclick="initUpdateRecord(' + row.id + ');">Update</a>';
    }
    return data;
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-danger" onclick="deleteRecord(' + row.id + ');">Delete</a>';
    }
    return data;
}

function showRestaurantDetails(id, url) {
    if (typeof url === 'undefined') {
        url = ajaxUrl;
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