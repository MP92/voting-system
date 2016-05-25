package ru.pkg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;
import ru.pkg.utils.exception.ExceptionUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository repository;

    @PostConstruct
    public void init() {
        LOG.info("\t init");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("\t preDestroy");
    }

    @Override
    public User findById(int id) {
        LOG.debug("findById {}", id);

        return ExceptionUtil.checkAndReturn(repository.findById(id), id);
    }

    @Override
    public void add(User user) {
        LOG.debug("add {}", user);

        repository.save(user);
    }

    @Override
    public void update(User user) {
        LOG.debug("update {}", user);

        ExceptionUtil.check(repository.save(user), user.getId());
    }

    @Override
    public void delete(int id) {
        LOG.debug("delete id={}", id);

        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public Collection<User> findAll() {
        LOG.debug("findAll");

        return repository.findAll();
    }
}
