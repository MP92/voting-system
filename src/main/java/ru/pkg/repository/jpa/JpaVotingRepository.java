package ru.pkg.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;
import ru.pkg.repository.VotingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVotingRepository implements VotingRepository {

    @PersistenceContext
    private EntityManager em;

    //todo add userId argument
    @Transactional
    public UserVote save(UserVote userVote) {
        if (findById(userVote.getUserId()) != null) {
            userVote.setUser(em.getReference(User.class, userVote.getUserId()));
            return em.merge(userVote);
        }
        return null;
    }

    public UserVote findById(int userId) {
        return em.find(UserVote.class, userId);
    }

    public List<UserVote> findAll() {
        return em.createQuery("SELECT uv FROM UserVote uv WHERE uv.restaurantId IS NOT NULL AND uv.lastVoted >= current_date() ORDER BY uv.id").getResultList();
    }

    @Transactional
    public boolean delete(int userId) {
        return em.createQuery("UPDATE UserVote uv SET uv.restaurantId=NULL, uv.lastVoted=NULL WHERE uv.user.id=:userId")
                .setParameter("userId", userId)
                .executeUpdate() > 0;
    }

    @Transactional
    public void reset() {
        em.createQuery("UPDATE UserVote uv SET uv.restaurantId=NULL, uv.lastVoted=NULL").executeUpdate();
    }
}
