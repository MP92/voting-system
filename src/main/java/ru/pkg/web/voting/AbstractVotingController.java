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
            service.save(LoggedUser.getId(), restaurantId);
        }
    }

    public void cancel() {
        service.delete(LoggedUser.getId());
    }

    public void reset() {
        service.reset();
    }

    public List<VotingStatistics> findVotingStatistics() {
        return service.findVotingStatistics();
    }
}
