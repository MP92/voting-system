var rootUrl = getContextPath() + '/rest/restaurants/';
var votingItemTemplate = Handlebars.getTemplate('votingItem');
var chart;

function vote(id) {
    $.ajax({
        type: "POST",
        url: rootUrl + id + '/vote',
        success: function () {
            updateVotingChart();
            alert('Request to vote performed');
        }
    });
}

function cancelVote() {
    $.ajax({
        type: "POST",
        url: rootUrl + 'voting/cancel',
        success: function () {
            updateVotingChart();
            alert('Request to cancel vote performed');
        }
    });
}

function resetVotes() {
    $.ajax({
        type: "PUT",
        url: rootUrl + 'voting/reset',
        success: function () {
            updateVotingChart();
            alert('Request to reset votes performed');
        }
    });
}

function updateVotingChart() {
    chart.html('');
    $.get(rootUrl + '/voting', function(data) {
        $.each(data, function(idx, obj) {
            chart.append(votingItemTemplate(obj));
        });
    });
}

$(function() {
    chart = $('.voting-chart');
    updateVotingChart();
});