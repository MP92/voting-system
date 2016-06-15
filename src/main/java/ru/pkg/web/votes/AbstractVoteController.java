package ru.pkg.web.votes;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.LoggedUser;
import ru.pkg.model.UserVote;
import ru.pkg.service.RestaurantService;
import ru.pkg.service.UserVoteService;
import ru.pkg.to.VotingStatistics;
import ru.pkg.utils.RestaurantUtil;

import java.util.List;

public abstract class AbstractVoteController {

    @Autowired
    UserVoteService voteService;

    @Autowired
    RestaurantService restaurantService;

    public void vote(int restaurantId) {
        UserVote userVote = voteService.findById(LoggedUser.getId());
        if (!userVote.isVotedToday()) {
            voteService.save(new UserVote(LoggedUser.getId(), restaurantId));
        }
    }

    public void cancelVote() {
        voteService.delete(LoggedUser.getId());
    }

    public void reset() {
        voteService.reset();
    }

    public List<VotingStatistics> getVotingStatistics() {
        return RestaurantUtil.getStatistics(restaurantService.findAll());
    }
}
