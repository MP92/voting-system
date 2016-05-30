package ru.pkg.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class User extends NamedEntity {

    private String password;

    private LocalDateTime registered;

    private LocalDateTime lastVoted;

    private boolean enabled;

    private Set<Role> roles;

    public User() {
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.password, user.registered, user.lastVoted, user.enabled, user.roles);
    }

    public User(Integer id, String name, String password, Role role, Role... roles) {
        this(id, name, password, LocalDateTime.now(), null, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String password, LocalDateTime registered, boolean enabled, Set<Role> roles) {
        this(id, name, password, registered, null, enabled, roles);
    }

    public User(Integer id, String name, String password, LocalDateTime registered, LocalDateTime lastVoted, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.password = password;
        this.registered = registered;
        this.lastVoted = lastVoted;
        this.enabled = enabled;
        this.roles = roles != null ? EnumSet.copyOf(roles) : Collections.emptySet();
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

    public void vote() {
        lastVoted = LocalDateTime.now();
    }

    public boolean neverVoted() {
        return lastVoted == null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", roles=" + roles +
                ", registered=" + registered +
                ", lastVoted=" + lastVoted +
                ", enabled=" + enabled +
                '}';
    }
}
