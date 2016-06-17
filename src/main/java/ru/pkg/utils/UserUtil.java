package ru.pkg.utils;

import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.model.UserVote;
import ru.pkg.to.UserTO;
import ru.pkg.to.UserWithVote;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserUtil {
    public static User createFromTO(UserTO userTO) {
        return new User(userTO.getId(), userTO.getName(), userTO.getSurname(), userTO.getPassword(), Role.ROLE_USER);
    }

    public static User updateFromTO(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setSurname(userTO.getSurname());
        user.setPassword(userTO.getPassword());
        return user;
    }

    public static UserTO asTO(User user) {
        return new UserTO(user.getId(), user.getName(), user.getSurname(), user.getPassword());
    }

    public static List<UserWithVote> createWithVotes(List<User> users, List<UserVote> userVotes) {
        Map<Integer, UserVote> userVoteMap = userVotes.stream().collect(Collectors.toMap(UserVote::getUserId, Function.identity()));
        UserVote defaultUserVote = new UserVote();
        return users.stream().map(user -> new UserWithVote(user, userVoteMap.getOrDefault(user.getId(), defaultUserVote))).collect(Collectors.toList());
    }
}
