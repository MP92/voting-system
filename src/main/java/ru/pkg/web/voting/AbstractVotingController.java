package ru.pkg.web.voting;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.LoggedUser;
import ru.pkg.model.UserVote;
import ru.pkg.service.VotingService;
import ru.pkg.to.VotingStatistics;

import java.util.List;

public abstract class AbstractVotingController {

    @Autowired
    VotingService service;

    public void vote(int restaurantId) {
        UserVote userVote = service.findById(LoggedUser.getId());
        if (!userVote.isVotedToday()) {
            userVote = service.save(LoggedUser.getId(), restaurantId);
            LoggedUser.getUserTO().setLastVoted(userVote.getLastVoted());
        }
    }

    public void cancel() {
        service.delete(LoggedUser.getId());
        LoggedUser.getUserTO().setLastVoted(null);
    }

    public void reset() {
        service.reset();
        LoggedUser.getUserTO().setLastVoted(null);
    }

    public List<VotingStatistics> findVotingStatistics() {
        return service.findVotingStatistics();
    }
}
