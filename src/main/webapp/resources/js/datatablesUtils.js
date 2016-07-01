var form;

$(function () {
    form = $('#detailsForm');

    form.submit(function () {
        save();
        return false;
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
            showSuccessToast('Deleted');
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
            showSuccessToast('Saved');
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