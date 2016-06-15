package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.pkg.model.UserVote;
import ru.pkg.repository.UserVoteRepository;
import ru.pkg.utils.exception.UserNotFoundException;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

@Service
public class UserVoteServiceImpl implements UserVoteService {

    @Autowired
    UserVoteRepository repository;

    @Override
    public void save(UserVote userVote) throws VotingException {
        try {
            repository.save(userVote);
        } catch (DataIntegrityViolationException e) {
            throw new VotingException(e);
        }
    }

    @Override
    public UserVote findById(int userId) throws VotingException {
        UserVote userVote = repository.findById(userId);
        if (userVote == null) {
            throw new VotingException(userId);
        }
        return userVote;
    }

    @Override
    public List<UserVote> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(int userId) throws VotingException {
        if (!repository.delete(userId)) {
            throw new VotingException(userId);
        }
    }

    @Override
    public void reset() {
        repository.reset();
    }
}
