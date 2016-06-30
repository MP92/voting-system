$(function() {
    var chart = $('.voting-chart');
    var template = Handlebars.getTemplate('votingItem');
    $.get(getContextPath() + '/rest/restaurants/voting', function(data) {
        $.each(data, function(idx, obj) {
            chart.append(template(obj));
        });
    });
});