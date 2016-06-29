var ajaxUrl = getContextPath() + '/ajax/restaurants/';
var detailsInfo;
$(function() {
    detailsInfo = $('#detailsInfo');

    var catalogItems = $('.catalog-items');
    var template = Handlebars.compile($("#item-template").html());
    $.get(ajaxUrl, function(data) {
        $.each(data, function(idx, obj) {
            catalogItems.append(template(obj));
        });
    });
});