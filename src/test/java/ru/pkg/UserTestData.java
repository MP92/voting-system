package ru.pkg;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Role;
import ru.pkg.model.User;

import java.time.LocalDateTime;
import java.util.*;

public class UserTestData {

    public static final int START_INDEX = 1;

    public static final int ADMIN_ID = START_INDEX;
    public static final int USER_ID = START_INDEX + 1;

    public static final int NEW_USER_ID = START_INDEX + 100;

    public static final int NOT_FOUND_INDEX = 100000;

    public static final ModelMatcher<User, TestUser> MATCHER = new ModelMatcher<>(u -> (u instanceof TestUser ? (TestUser)u : new TestUser(u)));

    public static final LocalDateTime TEST_DT = LocalDateTime.of(2016, 1, 1, 0, 0);

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "Adminov", "admin", TEST_DT, TEST_DT, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));
    public static final User USER = new User(USER_ID, "User", "Userov", "user", TEST_DT, TEST_DT, true, EnumSet.of(Role.ROLE_USER));

    public static final User NEW_USER = new User(null, "test", "test", "test", TEST_DT, TEST_DT, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));

    public static final List<User> ALL_USERS = Arrays.asList(ADMIN, USER);

    public static class TestUser extends User {

        public TestUser(User user){
            this(user.getId(), user);
        }

        public TestUser(Integer id, User user){
            super(id, user.getName(), user.getSurname(), user.getPassword(), user.getRegistered(), user.getLastVoted(), user.isEnabled(), user.getRoles());
        }

        public User asUser() {
            return new User(getId(), getName(), getSurname(), getPassword(), getRegistered(), getLastVoted(), isEnabled(), getRoles());
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
            return Objects.equals(this.getRoles(), that.getRoles())
                    && Objects.equals(this.getPassword(), that.getPassword())
                    && Objects.equals(this.getId(), that.getId())
                    && Objects.equals(this.getName(), that.getName())
                    && Objects.equals(this.getSurname(), that.getSurname())
                    && Objects.equals(this.getLastVoted(), that.getLastVoted())
                    && Objects.equals(this.isEnabled(), that.isEnabled());
        }

        @Override
        public String toString() {
            return "User (" +
                    "id=" + getId() +
                    ", name=" + getName() +
                    ", surname=" + getSurname() +
                    ", registered=" + getRegistered() +
                    ", lastVoted=" + getLastVoted() +
                    ", enabled=" + isEnabled() +
                    ", password=" + getPassword() +
                    ", authorities=" + getRoles() +
                    ')';
        }
    }
}
