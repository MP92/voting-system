package ru.pkg.model;

import java.time.LocalDateTime;

public class UserVote {

    private Integer userId;

    private Integer restaurantId;

    private LocalDateTime dateTime;

    public UserVote() {
    }

    public UserVote(int userId) {
        this.userId = userId;
    }

    public UserVote(int userId, int restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public UserVote(int userId, int restaurantId, LocalDateTime dateTime) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.dateTime = dateTime;
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

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isVotedToday() {
        return dateTime != null && dateTime.isAfter(dateTime.toLocalDate().atStartOfDay());
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", dateTime=" + dateTime +
                '}';
    }
}
