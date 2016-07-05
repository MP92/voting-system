package ru.pkg.web.voting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.pkg.LoggedUser;
import ru.pkg.model.UserVote;
import ru.pkg.service.VotingService;
import ru.pkg.to.VotingStatistics;
import ru.pkg.utils.TimeUtil;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

public abstract class AbstractVotingController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    VotingService service;

    public void vote(int restaurantId) {
        UserVote userVote = service.findById(LoggedUser.getId());
        if (!TimeUtil.isCanVote() || userVote.isVotedToday()) {
            String code = TimeUtil.isCanVote() ? "exception.voting.already_voted" : "exception.voting.over";
            throw new VotingException(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));
        }

        userVote = service.save(LoggedUser.getId(), restaurantId);
        LoggedUser.getUserTO().setLastVoted(userVote.getLastVoted());
    }

    public void cancel() {
        if (!TimeUtil.isCanVote()) {
            throw new VotingException(messageSource.getMessage("exception.voting.over", null, LocaleContextHolder.getLocale()));
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
