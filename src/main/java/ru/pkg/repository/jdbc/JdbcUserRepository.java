package ru.pkg.repository.jdbc;

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
import ru.pkg.repository.VotingRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository extends NamedParameterJdbcDaoSupport implements UserRepository {

    private static final RowMapper<User> USER_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<Role> ROLES_MAPPER = (rs, rowNum) -> Role.valueOf(rs.getString("role"));

    private SimpleJdbcInsert inserter;

    @Autowired
    public JdbcUserRepository(DataSource dataSource) {
        setDataSource(dataSource);
        this.inserter = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Autowired
    VotingRepository votingRepository;

    @Override
    public User save(User user) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number key = inserter.executeAndReturnKey(parameters);
            user.setId(key.intValue());
            insertVoteRecord(user);
        } else {
            String queryUpdate = "UPDATE users SET name=:name, surname=:surname, password=:password, registered=:registered, enabled=:enabled WHERE id=:id";
            if (getNamedParameterJdbcTemplate().update(queryUpdate, parameters) == 0) {
                return null;
            }
            deleteRoles(user);
        }
        insertRoles(user);

        return user;
    }

    @Override
    public User findById(int id) {
        User user = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT * FROM users WHERE id=?", USER_MAPPER, id));
        loadRoles(user);
        loadUserVote(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users as u LEFT JOIN votes as v ON (u.id=v.user_id) LEFT JOIN roles as r ON (u.id=r.user_id) ORDER BY u.name, u.registered";
        return getJdbcTemplate().query(query, new UsersExtractor());
    }

    @Override
    public boolean delete(int id) {
        return getJdbcTemplate().update("DELETE FROM users WHERE users.id=?", id) != 0;
    }

    private void insertRoles(User u) {
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
        getJdbcTemplate().execute("DELETE FROM roles WHERE user_id=" + u.getId());
    }

    private void loadRoles(User u) {
        if (u != null) {
            List<Role> roles = getJdbcTemplate().query("SELECT role FROM roles WHERE roles.user_id=?", ROLES_MAPPER, u.getId());
            u.setRoles(roles);
        }
    }

    private void loadUserVote(User u) {
        if (u != null) {
            u.setUserVote(votingRepository.findById(u.getId()));
        }
    }

    private void insertVoteRecord(User u) {
        getJdbcTemplate().update("INSERT INTO votes (user_id, restaurant_id, last_voted) VALUES (?, NULL, NULL)", u.getId());
    }

    private class UsersExtractor implements ResultSetExtractor<List<User>> {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> userMap = new LinkedHashMap<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                Role role =  Role.valueOf(rs.getString("role"));

                User user = userMap.get(id);
                if (user != null) {
                    user.addRole(role);
                } else {
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String password = rs.getString("password");
                    LocalDateTime registered = rs.getTimestamp("registered").toLocalDateTime();
                    boolean enabled = rs.getBoolean("enabled");
                    User newUser = new User(id, name, surname, password, registered, enabled, EnumSet.of(role));
                    newUser.setUserVote(votingRepository.findById(id));
                    userMap.put(id, newUser);
                }
            }

            return userMap.values().stream().collect(Collectors.toList());
        }
    }
}
