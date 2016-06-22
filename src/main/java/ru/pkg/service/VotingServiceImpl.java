package ru.pkg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.UserVote;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.repository.VotingRepository;
import ru.pkg.to.VotingStatistics;
import ru.pkg.utils.VotingUtil;
import ru.pkg.utils.exception.VotingException;

import java.util.List;

@Service
public class VotingServiceImpl implements VotingService {

    @Autowired
    VotingRepository votingRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public void save(UserVote userVote) throws VotingException {
        try {
            if (votingRepository.save(userVote) == null) {
                throw new VotingException(userVote.getUserId());
            }
        } catch (DataIntegrityViolationException e) {
            throw new VotingException(e);
        }
    }

    @Override
    public UserVote findById(int userId) {
        UserVote userVote = votingRepository.findById(userId);
        if (userVote == null) {
            throw new VotingException(userId);
        }
        return userVote;
    }

    @Override
    public List<UserVote> findAll() {
        return votingRepository.findAll();
    }

    @Override
    public void delete(int userId) throws VotingException {
        if (!votingRepository.delete(userId)) {
            throw new VotingException(userId);
        }
    }

    @Override
    public void reset() {
        votingRepository.reset();
    }

    @Transactional(readOnly = true)
    public List<VotingStatistics> findVotingStatistics() {
        return VotingUtil.getVotingStatistics(restaurantRepository.findAll(), votingRepository.findAll());
    }
}
