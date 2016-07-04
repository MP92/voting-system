$(function () {
    $('[data-toggle="tooltip"]').tooltip();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/\{(\d+)\}/g, function (m, n) { return args[n]; });
};

function setHighlight(elem, isHighlight) {
    if (isHighlight) {
        elem.addClass('row-highlight');
    }
    else {
        elem.removeClass('row-highlight');
    }
}

function isToday(date) {
    date = new Date(date);
    return date.toDateString() == new Date().toDateString();
}

function isUserVotedToday(profileData) {
    return isToday(profileData['lastVoted']);
}

function enableBtn(btn, onClickHandler) {
    btn.removeClass('btn-disabled');
    btn.off('click');
    btn.click(onClickHandler);
}

function disableBtn(btn) {
    btn.addClass('btn-disabled');
    btn.off('click');
    btn.click(function(event) {
        event.preventDefault();
    });
}