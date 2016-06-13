package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.User;
import ru.pkg.model.Votes;
import ru.pkg.repository.UserRepository;
import ru.pkg.repository.VotesRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VotesServiceImpl implements VotesService {

    @Autowired
    VotesRepository votesRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void vote(int userId, int restaurantId) {
        votesRepository.up(restaurantId);
        updateUserVoteDate(userId);
    }

    @Transactional
    @Override
    public void unvote(int userId, int restaurantId) {
        votesRepository.down(restaurantId);
        updateUserVoteDate(userId);
    }

    @Override
    public Votes findById(int restaurantId) {
        return votesRepository.findById(restaurantId);
    }

    @Override
    public List<Votes> findAll() {
        return votesRepository.findAll();
    }

    @Override
    public Integer findCount(int restaurantId) {
        return votesRepository.findCount(restaurantId);
    }

    @Override
    public void reset() {
        votesRepository.reset();
    }

    private void updateUserVoteDate(int userId) {
        User user = userRepository.findById(userId);
        user.setLastVoted(LocalDateTime.now());
        userRepository.save(user);
    }
}
