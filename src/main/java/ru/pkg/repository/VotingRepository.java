package ru.pkg.repository;

import ru.pkg.model.UserVote;

import java.util.List;

public interface VotingRepository {

    UserVote save(int userId, int restaurantId);

    UserVote findById(int userId);

    List<UserVote> findAll();

    boolean delete(int userId);

    void reset();
}
