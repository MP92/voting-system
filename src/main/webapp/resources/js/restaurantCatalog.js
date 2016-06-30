var catalogItemTemplate = Handlebars.getTemplate('catalogItem');
var voteBtnPattern = '<div class="text-center"><a class="voting-button btn-vote" onclick="vote({0})">Vote</a></div>';

$(function() {
    var catalogItems = $('.catalog-items');
    $.get(getContextPath() + '/ajax/restaurants/', function(data) {
        $.each(data, function(idx, obj) {
            var catalogItem = $('<div/>').html(catalogItemTemplate(obj)).contents();
            catalogItem.append(voteBtnPattern.format(obj['id']));
            catalogItems.append(catalogItem);
        });
    });
});