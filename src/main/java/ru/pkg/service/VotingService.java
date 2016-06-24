package ru.pkg.service;

import ru.pkg.model.UserVote;
import ru.pkg.to.VotingStatistics;
import ru.pkg.utils.exception.RestaurantNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

public interface VotingService {

    UserVote save(int userId, int restaurantId) throws VotingException, RestaurantNotFoundException;

    UserVote findById(int userId);

    List<UserVote> findAll();

    void delete(int userId) throws VotingException;

    void reset();

    List<VotingStatistics> findVotingStatistics();
}
