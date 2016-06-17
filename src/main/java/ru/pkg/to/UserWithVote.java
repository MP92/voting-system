package ru.pkg.to;

import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
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
        this(user.getId(), user.getName(), user.getSurname(), user.getPassword(), user.getRegistered(), user.isEnabled(), user.getRoles(), userVote.getRestaurantId(), userVote.getDateTime());
    }

    public UserWithVote(Integer id, String name, String surname, String password, LocalDateTime registered, boolean enabled, Set<Role> roles, Integer votedRestaurantId, LocalDateTime lastVoted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.registered = registered;
        this.enabled = enabled;
        this.roles = roles;
        this.votedRestaurantId = votedRestaurantId;
        this.lastVoted = lastVoted;
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

    @Override
    public String toString() {
        return "UserWithVote{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", registered=" + registered +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", votedRestaurantId=" + votedRestaurantId +
                ", lastVoted=" + lastVoted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWithVote that = (UserWithVote) o;

        return Objects.equals(this.enabled, that.enabled)
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.surname, that.surname)
                && Objects.equals(this.password, that.password)
                && Objects.equals(this.registered, that.registered)
                && Objects.equals(this.roles, that.roles)
                && Objects.equals(this.votedRestaurantId, that.votedRestaurantId)
                && Objects.equals(this.lastVoted, that.lastVoted);
    }
}
