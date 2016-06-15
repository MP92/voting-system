package ru.pkg.to;

import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class UserWithVote {

    private Integer id;

    private String name;

    String surname;

    private String password;

    private LocalDateTime registered;

    private boolean enabled;

    private Set<Role> roles = Collections.emptySet();

    private Integer votedRestaurantId;

    private LocalDateTime lastVoted;

    public UserWithVote() {
    }

    public UserWithVote(User user, UserVote userVote) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.password = user.getPassword();
        this.registered = user.getRegistered();
        this.enabled = user.isEnabled();
        this.roles = user.getRoles();
        this.votedRestaurantId = userVote.getRestaurantId();
        this.lastVoted = userVote.getDateTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getVotedRestaurantId() {
        return votedRestaurantId;
    }

    public void setVotedRestaurantId(int votedRestaurantId) {
        this.votedRestaurantId = votedRestaurantId;
    }

    public LocalDateTime getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(LocalDateTime lastVoted) {
        this.lastVoted = lastVoted;
    }
}
