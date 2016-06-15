package ru.pkg.repository;

import ru.pkg.model.UserVote;

import java.util.List;

public interface UserVoteRepository {

    UserVote save(UserVote userVote);

    UserVote findById(int userId);

    List<UserVote> findAll();

    boolean delete(int userId);

    void reset();
}
