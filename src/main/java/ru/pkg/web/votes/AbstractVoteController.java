package ru.pkg.web.votes;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.LoggedUser;
import ru.pkg.model.Votes;
import ru.pkg.service.VotesService;

import java.util.List;
import java.util.Map;

public abstract class AbstractVoteController {

    @Autowired
    VotesService service;

    public void vote(int restaurantId) {
        service.vote(LoggedUser.getId(), restaurantId);
    }

    public Votes findById(int restaurantId) {
        return service.findById(restaurantId);
    }

    public List<Votes> findAll() {
        return service.findAll();
    }

    public Integer findCount(int restaurantId) {
        return service.findCount(restaurantId);
    }

    public void reset() {
        service.reset();
    }

    public void unvote(int restaurantId) {
        service.unvote(LoggedUser.getId(), restaurantId);
    }
}
