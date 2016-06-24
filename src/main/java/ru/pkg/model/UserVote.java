package ru.pkg.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.pkg.utils.TimeUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class UserVote extends BaseEntity {

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonBackReference
    private User user;

    //todo try to delete
    @Column(name = "restaurant_id", nullable = true)
    private Integer restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant;

    @Column(name = "last_voted", nullable = true)
    private LocalDateTime lastVoted;

    public UserVote() {
        this.lastVoted = LocalDateTime.now();
    }

    public UserVote(int userId) {
        this(userId, null);
    }

    public UserVote(int userId, Integer restaurantId) {
        this(userId, restaurantId, null);
        this.lastVoted = LocalDateTime.now();
    }

    public UserVote(int userId, Integer restaurantId, LocalDateTime lastVoted) {
        super(userId);
        this.restaurantId = restaurantId;
        this.lastVoted = lastVoted;
    }

    public Integer getUserId() {
        return getId();
    }

    public void setUserId(int userId) {
        setId(userId);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isVotedToday() {
        return (restaurant != null || restaurantId != null) && TimeUtil.isToday(lastVoted);
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "userId=" + getUserId() +
                ", restaurantId=" + restaurantId +
                ", lastVoted=" + lastVoted +
                '}';
    }
}
