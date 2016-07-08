package ru.pkg.model;

import com.fasterxml.jackson.annotation.*;
import ru.pkg.utils.TimeUtil;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class UserVote extends BaseEntity {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private User user;

    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "last_voted", columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime lastVoted;

    public UserVote() {
        this.lastVoted = LocalDateTime.now();
    }

    public UserVote(int userId) {
        this(userId, null);
    }

    public UserVote(int userId, Integer restaurantId) {
        this(userId, restaurantId, LocalDateTime.now());
    }

    public UserVote(int userId, Integer restaurantId, LocalDateTime lastVoted) {
        super(userId);
        this.restaurantId = restaurantId;
        this.lastVoted = lastVoted;
    }

    public int getUserId() {
        return getId();
    }

    public void setUserId(int id) {
        setId(id);
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
        return restaurantId != null && TimeUtil.isToday(lastVoted);
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "userId=" + getId() +
                ", restaurantId=" + restaurantId +
                ", lastVoted=" + lastVoted +
                '}';
    }
}
