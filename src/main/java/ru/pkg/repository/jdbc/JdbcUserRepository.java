package ru.pkg.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcUserRepository extends NamedParameterJdbcDaoSupport implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcUserRepository.class);

    private static final RowMapper<User> USER_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<Role> ROLES_MAPPER = (rs, rowNum) -> Role.valueOf(rs.getString("role"));

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(DataSource dataSource) {
        LOG.debug("constructing");
        setDataSource(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User findById(int id) {
        LOG.debug("findById {}", id);
        User user = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM users WHERE users.id=?", USER_MAPPER, id));
        fetchRoles(user);
        return user;
    }

    @Override
    public User save(User user) {
        LOG.debug("save {}", user);

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number key = insertUser.executeAndReturnKey(parameters);
            user.setId(key.intValue());
            insertRoles(user);
        } else {
            String queryUpdate = "UPDATE users SET name=:name, password=:password, registered=:registered, " +
                    (!user.neverVoted() ? "last_voted=:lastVoted, " : "") + "enabled=:enabled WHERE id=:id";

            if (!containsUser(user) || getNamedParameterJdbcTemplate().update(queryUpdate, parameters) == 0) {
                return null;
            }
            deleteRoles(user);
            insertRoles(user);
        }

        return user;
    }

    @Override
    public boolean delete(int id) {
        LOG.debug("delete user with id={}", id);

        return getJdbcTemplate().update("DELETE FROM users WHERE users.id=?", id) != 0;
    }

    @Override
    public Collection<User> findAll() {
        LOG.debug("findAll");
        String query = "SELECT * FROM users as u LEFT JOIN roles as r ON u.id=r.user_id ORDER BY u.name, u.registered";
        return getJdbcTemplate().query(query, new UsersExtractor());
    }

    @Override
    public void clear() {
        LOG.debug("clear");
        getJdbcTemplate().update("DELETE FROM users");
    }


    private void insertRoles(User u) {
        LOG.debug("insertRoles for {}", u);

        Iterator<Role> roleIterator = u.getRoles().iterator();

        getJdbcTemplate().batchUpdate("INSERT INTO roles(user_id, role) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, u.getId());
                            ps.setString(2, roleIterator.next().toString());
                        }

                        @Override
                        public int getBatchSize() {
                            return u.getRoles().size();
                        }
                }
        );
    }

    private void deleteRoles(User u) {
        LOG.debug("deleteRoles for {}", u);
        getJdbcTemplate().execute("DELETE FROM roles WHERE user_id=" + u.getId());
    }

    private void fetchRoles(User u) {
        LOG.debug("setRoles for {}", u);
        if (u != null) {
            List<Role> roles = getJdbcTemplate().query("SELECT role FROM roles WHERE roles.user_id=?", ROLES_MAPPER, u.getId());
            u.setRoles(roles);
        }
    }

    private boolean containsUser(User u) {
        return getJdbcTemplate().query("SELECT id from users WHERE id=?", USER_MAPPER, u.getId()).size() > 0;
    }

    private class UsersExtractor implements ResultSetExtractor<Collection<User>> {
        @Override
        public Collection<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> userMap = new LinkedHashMap<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                Role role =  Role.valueOf(rs.getString("role"));

                User user = userMap.get(id);
                if (user != null) {
                    user.addRole(role);
                } else {
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    LocalDateTime registered = rs.getTimestamp("registered").toLocalDateTime();
                    Timestamp ts = rs.getTimestamp("last_voted");
                    LocalDateTime lastVoted = ts != null ? ts.toLocalDateTime() : null;
                    boolean enabled = rs.getBoolean("enabled");
                    userMap.put(id, new User(id, name, password, registered, lastVoted, enabled, EnumSet.of(role)));
                }
            }

            return userMap.values();
        }
    }
}
