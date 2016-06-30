$(function() {
    var catalogItems = $('.catalog-items');
    var template = Handlebars.getTemplate('catalogItem');
    $.get(getContextPath() + '/ajax/restaurants/', function(data) {
        $.each(data, function(idx, obj) {
            catalogItems.append(template(obj));
        });
    });
});