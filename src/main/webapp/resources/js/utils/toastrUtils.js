toastr.options.closeButton = true;

function showErrorToast(event, jqXHR, options, jsExc) {
    toastr.clear();
    var errorInfo = $.parseJSON(jqXHR.responseText);
    toastr.error(errorInfo.cause + '<br>' + errorInfo.detail, messages['failed'] + jqXHR.statusText);
}

$(function () {
    $(document).ajaxError(showErrorToast);
});

function showSuccessToast(message) {
    toastr.clear();
    toastr.success(message);
}