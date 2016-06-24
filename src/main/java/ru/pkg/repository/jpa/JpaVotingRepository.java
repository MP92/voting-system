package ru.pkg.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;
import ru.pkg.repository.VotingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVotingRepository implements VotingRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public UserVote save(int userId, int restaurantId) {
        if (findById(userId) != null) {
            UserVote userVote = new UserVote(userId, restaurantId);
            userVote.setUser(em.getReference(User.class, userId));
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
