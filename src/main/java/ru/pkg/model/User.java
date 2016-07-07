package ru.pkg.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

import static ru.pkg.utils.constants.EntityConstraints.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "users_unique_name_idx", columnNames = {"name", "surname"}))
public class User extends NamedEntity {

    @Column(name = "surname", nullable = false)
    @NotEmpty
    @Size(min = NAME_MIN, max = NAME_MAX)
    private String surname;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @Size(min = PASSWORD_MIN, max = PASSWORD_MAX)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    @NotNull
    private LocalDateTime registered;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Collections.emptySet();

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
    private UserVote userVote;

    public User() {
    }

    public User(Integer id, String name, String surname, String password, Role role, Role... roles) {
        this(id, name, surname, password, LocalDateTime.now(), true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String surname, String password, LocalDateTime registered, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.surname = surname;
        this.password = password;
        this.registered = registered;
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
        return userVote != null ? userVote.getLastVoted() : null;
    }

    public boolean isVotedToday() {
        return userVote.isVotedToday();
    }

    public UserVote getUserVote() {
        return userVote;
    }

    public void setUserVote(UserVote userVote) {
        this.userVote = userVote;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", surname=" + getSurname() +
                ", roles=" + roles +
                ", registered=" + registered +
                ", userVote=" + userVote +
                ", enabled=" + enabled +
                '}';
    }
}
