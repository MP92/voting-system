package ru.pkg.service;

import ru.pkg.model.UserVote;
import ru.pkg.utils.exception.UserNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

public interface UserVoteService {

    void save(UserVote userVote) throws VotingException;

    UserVote findById(int userId) throws VotingException;

    List<UserVote> findAll();

    void delete(int userId) throws VotingException;

    void reset();
}
