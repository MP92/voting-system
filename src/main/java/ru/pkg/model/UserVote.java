package ru.pkg.model;

import java.time.LocalDateTime;

public class UserVote extends BaseEntity {

    private Integer userId;

    private Integer restaurantId;

    private LocalDateTime lastVoted;

    public UserVote() {
    }

    public UserVote(int userId) {
        this.userId = userId;
    }

    public UserVote(int userId, Integer restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public UserVote(int userId, Integer restaurantId, LocalDateTime lastVoted) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.lastVoted = lastVoted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(LocalDateTime lastVoted) {
        this.lastVoted = lastVoted;
    }

    public boolean isVotedToday() {
        return restaurantId != null && lastVoted != null && lastVoted.isAfter(lastVoted.toLocalDate().atStartOfDay());
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", lastVoted=" + lastVoted +
                '}';
    }
}
