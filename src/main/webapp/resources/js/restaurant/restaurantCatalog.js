var rootUrl = getContextPath() + '/ajax/restaurants/';
var catalogItemTemplate = Handlebars.getTemplate('catalogItem');
var votingItemTemplate = Handlebars.getTemplate('votingItem');
var chart;
var cancelBtn;

var voteBtnPattern = '<a class="voting-button btn-vote" onclick="vote({0})">Vote</a>';

$(function() {
    chart = $('.voting-chart');
    cancelBtn = $('.chart-footer .btn-cancel');

    getProfileAndThen(loadRestaurantCatalog);
});

function getProfileAndThen(action) {
    $.get(getContextPath() + '/ajax/profile/', function(data) {
        action(data);
    });
}

function loadRestaurantCatalog(profileData) {
    var catalogItems = $('.catalog-items');
    var votedToday = isUserVotedToday(profileData);
    $.get(rootUrl, function(data) {
        $.each(data, function(idx, obj) {
            var catalogItem = $('<div/>').html(catalogItemTemplate(obj)).contents();
            if (!votedToday) {
                catalogItem.find('.catalog-footer').append(voteBtnPattern.format(obj['id']));
            }
            catalogItems.append(catalogItem);
        });
        votedToday ? enableBtn(cancelBtn, cancelVote) : disableBtn(cancelBtn);
        updateVotingChart();
    });
}

function updateVotingChart() {
    $.get(rootUrl + 'voting', function(data) {
        chart.empty();
        $.each(data, function(idx, obj) {
            chart.append(votingItemTemplate(obj));
        });
    });
}

function addVoteButtons() {
    $.each($('.catalog-item'), function () {
        var id = $(this).attr('id');
        if (!$(this).find('.voting-button').length) {
            $(this).find('.catalog-footer').append(voteBtnPattern.format(id));
        }
    });
    disableBtn($('.chart-footer .btn-cancel'));
}

function removeVoteButtons() {
    $('.catalog-item .btn-vote').remove();
    enableBtn($('.chart-footer .btn-cancel'), cancelVote);
}

function vote(id) {
    $.ajax({
        type: "POST",
        url: rootUrl + id + '/vote',
        success: function () {
            updateVotingChart();
            showSuccessToast('Your vote accepted');
            removeVoteButtons();
        }
    });
}

function cancelVote() {
    $.ajax({
        type: "POST",
        url: rootUrl + 'voting/cancel',
        success: function () {
            updateVotingChart();
            showSuccessToast('Your vote canceled');
            addVoteButtons();
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
            addVoteButtons();
        }
    });
}