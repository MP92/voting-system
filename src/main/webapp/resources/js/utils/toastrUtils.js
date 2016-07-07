toastr.options.closeButton = true;

function showErrorToast(event, jqXHR, options, jsExc) {
    toastr.clear();
    try {
        var errorInfo = $.parseJSON(jqXHR.responseText);
        toastr.error(errorInfo.cause + "<br>" + errorInfo.detail, messages["failed"] + jqXHR.statusText);
    } catch(e) {
        toastr.error(messages["exception.default_message"]);
    }
}

$(function () {
    $(document).ajaxError(showErrorToast);
});

function showSuccessToast(message) {
    toastr.clear();
    toastr.success(message);
}