package ru.pkg.model;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class User extends NamedEntity {

    private Set<Role> roles;

    private LocalDate registered = LocalDate.now();

    private String password;

    private boolean enabled;

    public User(Integer id, String name, String password, boolean enabled, Role role, Role... roles) {
        super(id, name);
        this.roles = EnumSet.of(role, roles);
        this.password = password;
        this.enabled = enabled;
    }

    public User(Integer id, String name, String password, Role role, Role... roles) {
        super(id, name);
        this.roles = EnumSet.of(role, roles);
        this.password = password;
        this.enabled = true;
    }

    public User(User user) {
        this(user.getId(), user);
    }

    public User(Integer id, User user) {
        super(id, user.getName());
        this.roles = EnumSet.copyOf(user.roles);
        this.password = user.password;
        this.enabled = user.enabled;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
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
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
