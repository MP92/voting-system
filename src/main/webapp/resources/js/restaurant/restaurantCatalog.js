var rootUrl = ctx + "/ajax/restaurants/";
var catalogItemTemplate = Handlebars.getTemplate("catalogItem");
var votingItemTemplate = Handlebars.getTemplate("votingItem");
var chart;
var cancelBtn;

var HOUR_LIMIT = 22;

var voteBtnPattern = "<a class='voting-button btn-vote' onclick='vote({0})'>" + messages["vote"] + "</a>";

function getProfileAndThen(action) {
    $.get(ctx + "/ajax/profile/", function(data) {
        action(data);
    });
}

function updateVotingChart() {
    $.get(rootUrl + "voting", function (data) {
        chart.empty();
        $.each(data, function (idx, obj) {
            chart.append(votingItemTemplate(obj));
        });
    });
}

function addVoteButtons() {
    $.each($(".catalog-item"), function () {
        var id = $(this).attr("id");
        if (!$(this).find(".voting-button").length) {
            $(this).find(".catalog-footer").append(voteBtnPattern.format(id));
        }
    });
    disableBtn($(".chart-footer .btn-cancel"));
}

function cancelVote() {
    $.ajax({
        type: "POST",
        url: rootUrl + "voting/cancel",
        success() {
            updateVotingChart();
            showSuccessToast(messages["vote.canceled"]);
            addVoteButtons();
        }
    });
}

function loadRestaurantCatalog(profileData) {
    var catalogItems = $(".catalog-items");
    var canVote = new Date().getHours() < HOUR_LIMIT;
    var votedToday = isUserVotedToday(profileData);
    $.get(rootUrl, function(data) {
        $.each(data, function(idx, obj) {
            var catalogItem = $("<div/>").html(catalogItemTemplate(obj)).contents();
            catalogItem.find(".item-btn").text(messages["details"]);
            if (canVote && !votedToday) {
                catalogItem.find(".catalog-footer").append(voteBtnPattern.format(obj["id"]));
            }
            catalogItems.append(catalogItem);
        });
        canVote && votedToday ? enableBtn(cancelBtn, cancelVote) : disableBtn(cancelBtn);
        updateVotingChart();
    });
}

function removeVoteButtons() {
    $(".catalog-item .btn-vote").remove();
    enableBtn($(".chart-footer .btn-cancel"), cancelVote);
}

function vote(id) {
    $.ajax({
        type: "POST",
        url: rootUrl + id + "/vote",
        success() {
            updateVotingChart();
            showSuccessToast(messages["vote.accepted"]);
            removeVoteButtons();
        }
    });
}

function resetVotes() {
    $.ajax({
        type: "POST",
        url: rootUrl + "voting/reset",
        success() {
            updateVotingChart();
            showSuccessToast(messages["vote.cleared"]);
            addVoteButtons();
        }
    });
}

$(function() {
    chart = $(".voting-chart");
    cancelBtn = $(".chart-footer .btn-cancel");

    getProfileAndThen(loadRestaurantCatalog);
});