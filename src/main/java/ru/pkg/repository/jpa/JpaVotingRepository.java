package ru.pkg.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;
import ru.pkg.repository.VotingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaVotingRepository implements VotingRepository {

    @PersistenceContext
    private EntityManager em;

    public UserVote save(int userId, int restaurantId) {
        if (findById(userId) != null) {
            UserVote userVote = new UserVote(userId, restaurantId);
            userVote.setUser(em.getReference(User.class, userId));
            UserVote merged = em.merge(userVote);
            em.flush();
            return merged;
        }
        return null;
    }

    public UserVote findById(int userId) {
        return em.find(UserVote.class, userId);
    }

    public List<UserVote> findAll() {
        return em.createQuery("SELECT uv FROM UserVote uv WHERE uv.restaurantId IS NOT NULL AND uv.lastVoted >= current_date() ORDER BY uv.id").getResultList();
    }

    public boolean delete(int userId) {
        return em.createQuery("UPDATE UserVote uv SET uv.restaurantId=NULL, uv.lastVoted=NULL WHERE uv.user.id=:userId")
                .setParameter("userId", userId)
                .executeUpdate() > 0;
    }

    public void reset() {
        em.createQuery("UPDATE UserVote uv SET uv.restaurantId=NULL, uv.lastVoted=NULL").executeUpdate();
    }
}
