package ru.pkg.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pkg.model.User;
import ru.pkg.service.UserService;
import ru.pkg.service.UserVoteService;
import ru.pkg.to.UserTO;
import ru.pkg.to.UserWithVote;
import ru.pkg.utils.UserUtil;

import java.util.Collection;

public abstract class AbstractUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserVoteService userVoteService;

    public UserWithVote get(int id) {
        return new UserWithVote(userService.findById(id), userVoteService.findById(id));
    }

    public UserTO getForUpdate(int id) {
        return UserUtil.asTO(userService.findById(id));
    }

    public void delete(int id) {
        userService.delete(id);
    }

    public User create(User user) {
        user.setId(null);
        return userService.add(user);
    }

    public void update(int id, User user) {
        user.setId(id);
        userService.update(user);
    }

    public void update(UserTO user) {
        userService.update(user);
    }

    public Collection<UserWithVote> findAll() {
        return UserUtil.createWithVotes(userService.findAll(), userVoteService.findAll());
    }
}
