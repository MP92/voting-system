package ru.pkg.model;

import java.time.LocalDateTime;
import java.util.*;

public class User extends NamedEntity {

    private String surname;

    private String password;

    private LocalDateTime registered;

    private LocalDateTime lastVoted;

    private boolean enabled;

    private Set<Role> roles = Collections.emptySet();

    private Restaurant chosenRestaurant;

    public User() {
    }

    public User(Integer id, String name, String surname, String password, Role role, Role... roles) {
        this(id, name, surname, password, LocalDateTime.now(), null, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String surname, String password, LocalDateTime registered, boolean enabled, Set<Role> roles) {
        this(id, name, surname, password, registered, null, enabled, roles);
    }

    public User(Integer id, String name, String surname, String password, LocalDateTime registered, LocalDateTime lastVoted, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.surname = surname;
        this.password = password;
        this.registered = registered;
        this.lastVoted = lastVoted;
        this.enabled = enabled;
        if (roles != null) {
            this.roles = EnumSet.copyOf(roles);
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return !roles.isEmpty() ? EnumSet.copyOf(roles) : roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = EnumSet.copyOf(roles);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public LocalDateTime getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(LocalDateTime lastVoted) {
        this.lastVoted = lastVoted;
    }

    public boolean isVotedToday() {
        return lastVoted != null && lastVoted.isAfter(lastVoted.toLocalDate().atStartOfDay());
    }

    public Restaurant getChosenRestaurant() {
        return chosenRestaurant;
    }

    public void setChosenRestaurant(Restaurant chosenRestaurant) {
        this.chosenRestaurant = chosenRestaurant;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", surname=" + getSurname() +
                ", roles=" + roles +
                ", registered=" + registered +
                ", lastVoted=" + lastVoted +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        BaseEntity that = (BaseEntity) o;
        return Objects.equals(this.getId(), that.getId());
    }
}
