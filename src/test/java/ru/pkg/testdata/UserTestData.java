package ru.pkg.testdata;

import ru.pkg.matcher.ModelMatcher;
import ru.pkg.model.Role;
import ru.pkg.model.User;

import java.time.LocalDateTime;
import java.util.*;

import static ru.pkg.testdata.UserVoteTestData.VOTE_DT;

public class UserTestData {

    public static final int START_INDEX = 1;

    public static final int ADMIN_ID = START_INDEX;
    public static final int USER_1_ID = START_INDEX + 1;
    public static final int USER_2_ID = START_INDEX + 2;

    public static final int CREATED_USER_ID = START_INDEX + 3;

    public static final int NOT_FOUND_INDEX = 100000;

    public static final ModelMatcher<User, TestUser> MATCHER = new ModelMatcher<>(u -> (u instanceof TestUser ? (TestUser)u : new TestUser(u)));

    public static final LocalDateTime TEST_DT = LocalDateTime.of(2016, 1, 1, 0, 0);

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "Adminov", "admin", TEST_DT, VOTE_DT, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));
    public static final User USER_1 = new User(USER_1_ID, "User", "Userov", "user", TEST_DT, VOTE_DT, true, EnumSet.of(Role.ROLE_USER));
    public static final User USER_2 = new User(USER_2_ID, "User2", "Userov2", "user2", TEST_DT, null, true, EnumSet.of(Role.ROLE_USER));

    static {
        ADMIN.setChosenRestaurant(RestaurantTestData.RESTAURANT_1);
        USER_1.setChosenRestaurant(RestaurantTestData.RESTAURANT_2);
    }

    public static final User NEW_USER = new User(null, "test", "test", "password", TEST_DT, null, true, EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));

    public static final List<User> ALL_USERS = Arrays.asList(ADMIN, USER_1, USER_2);

    public static final int USERS_COUNT = 3;

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
                    && Objects.equals(this.isEnabled(), that.isEnabled())
                    && Objects.equals(this.getRegistered(), that.getRegistered());
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
