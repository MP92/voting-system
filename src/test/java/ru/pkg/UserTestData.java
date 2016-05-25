package ru.pkg;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Role;
import ru.pkg.model.User;

import java.util.*;

public class UserTestData {

    public static final User ADMIN = new User(null, "Admin", "admin", Role.ROLE_ADMIN);
    public static final User USER = new User(null, "User", "user", Role.ROLE_USER);

    public static final User NEW_USER = new User(null, "New User", "new", Role.ROLE_USER);

    public static final List<User> ALL_USERS = Arrays.asList(ADMIN, USER);

    public static final ModelMatcher<User, TestUser> MATCHER = new ModelMatcher<>(u -> (u instanceof TestUser ? (TestUser)u : new TestUser(u)));

    static class TestUser extends User {

        TestUser(User user) {
            super(user);
        }

        public User asUser() {
            return new User(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TestUser that = (TestUser) o;
            return !(this.getRoles() != null ? !this.getRoles().equals(that.getRoles()) : that.getRoles() != null)
                    && Objects.equals(this.getPassword(), that.getPassword())
                    && Objects.equals(this.getId(), that.getId())
                    && Objects.equals(this.getName(), that.getName())
                    && Objects.equals(this.isEnabled(), that.isEnabled());
        }

        @Override
        public String toString() {
            return "User (" +
                    "id=" + getId() +
                    ", name=" + getName() +
                    ", enabled=" + isEnabled() +
                    ", password=" + getPassword() +
                    ", authorities=" + getRoles() +
                    ')';
        }
    }
}
