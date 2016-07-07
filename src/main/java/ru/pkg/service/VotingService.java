package ru.pkg.service;

import ru.pkg.model.UserVote;
import ru.pkg.to.VotingStatistics;
import java.util.List;

public interface VotingService {

    UserVote save(int userId, int restaurantId);

    UserVote findById(int userId);

    List<UserVote> findAll();

    void delete(int userId);

    void reset();

    List<VotingStatistics> findVotingStatistics();
}
