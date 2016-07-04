package ru.pkg.web.voting;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.LoggedUser;
import ru.pkg.model.UserVote;
import ru.pkg.service.VotingService;
import ru.pkg.to.VotingStatistics;
import ru.pkg.utils.TimeUtil;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

public abstract class AbstractVotingController {

    @Autowired
    VotingService service;

    public void vote(int restaurantId) {
        UserVote userVote = service.findById(LoggedUser.getId());
        if (!TimeUtil.isCanVote() || userVote.isVotedToday()) {
            throw new VotingException(TimeUtil.isCanVote() ? "You have already voted today." : "Voting finished today.");
        }

        userVote = service.save(LoggedUser.getId(), restaurantId);
        LoggedUser.getUserTO().setLastVoted(userVote.getLastVoted());
    }

    public void cancel() {
        if (!TimeUtil.isCanVote()) {
            throw new VotingException("Voting finished today.");
        }
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
