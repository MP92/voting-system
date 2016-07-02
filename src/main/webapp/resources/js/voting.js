var rootUrl = getContextPath() + '/ajax/restaurants/';
var votingItemTemplate = Handlebars.getTemplate('votingItem');
var chart;

function vote(id) {
    $.ajax({
        type: "POST",
        url: rootUrl + id + '/vote',
        success: function () {
            updateVotingChart();
            showSuccessToast('Request to vote for restaurant performed');
        }
    });
}

function cancelVote() {
    $.ajax({
        type: "POST",
        url: rootUrl + 'voting/cancel',
        success: function () {
            updateVotingChart();
            showSuccessToast('Request to cancel vote for restaurant performed');
        }
    });
}

function resetVotes() {
    $.ajax({
        type: "POST",
        url: rootUrl + 'voting/reset',
        success: function () {
            updateVotingChart();
            showSuccessToast('Votes cleared');
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