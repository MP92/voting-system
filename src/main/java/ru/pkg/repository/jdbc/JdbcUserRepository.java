package ru.pkg.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.transaction.annotation.Transactional;
import ru.pkg.model.Role;
import ru.pkg.model.User;
import ru.pkg.repository.RestaurantRepository;
import ru.pkg.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository extends NamedParameterJdbcDaoSupport implements UserRepository {

    private static final RowMapper<JdbcUser> USER_MAPPER = BeanPropertyRowMapper.newInstance(JdbcUser.class);
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
    RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public User save(User user) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number key = inserter.executeAndReturnKey(parameters);
            user.setId(key.intValue());
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
        JdbcUser jdbcUser = DataAccessUtils.singleResult(getJdbcTemplate().query("SELECT u.*, v.restaurant_id AS chosenRestaurantId, v.last_voted FROM users as u LEFT JOIN votes as v ON (u.id=v.user_id) WHERE u.id=?", USER_MAPPER, id));
        loadRoles(jdbcUser);
        loadRestaurant(jdbcUser);
        return jdbcUser;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users as u LEFT JOIN votes as v ON (u.id=v.user_id) LEFT JOIN roles as r ON (u.id=r.user_id) ORDER BY u.name, u.registered";
        return getJdbcTemplate().query(query, new UsersExtractor());
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return getJdbcTemplate().update("DELETE FROM users WHERE users.id=?", id) != 0;
    }

    @Transactional
    public void saveVote(int userId, int restaurantId) {
        if (isUserVotedToday(userId)) {
            throw new DataIntegrityViolationException("User with id=" + userId + " already voted today");
        }
        if (getJdbcTemplate().update("UPDATE votes SET restaurant_id=?, last_voted=now() WHERE user_id=?", restaurantId, userId) == 0) {
            getJdbcTemplate().update("INSERT INTO votes (user_id, restaurant_id) VALUES (?, ?)", userId, restaurantId);
        }
        getJdbcTemplate().update("UPDATE voting_statistics SET votes = votes + 1 WHERE restaurant_id=?", restaurantId);
    }

    @Transactional
    public boolean deleteVote(int userId) {
        Integer restaurantId;
        try {
            restaurantId = getJdbcTemplate().queryForObject("SELECT restaurant_id FROM votes WHERE user_id=?", Integer.class, userId);
        } catch (DataAccessException e) {
            return false;
        }
        getJdbcTemplate().update("DELETE FROM votes WHERE user_id=?", userId);

        return getJdbcTemplate().update("UPDATE voting_statistics SET votes = GREATEST(votes - 1, 0) WHERE restaurant_id=?", restaurantId) > 0;
    }

    @Transactional
    public void resetVotes() {
        getJdbcTemplate().update("DELETE FROM votes");
        getJdbcTemplate().update("UPDATE voting_statistics SET votes = 0");
    }

    private boolean isUserVotedToday(int userId) {
        return getJdbcTemplate().queryForObject("SELECT exists(SELECT 1 FROM votes WHERE user_id=? AND last_voted >= now()::date)", Boolean.class, userId);
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

    private void loadRestaurant(JdbcUser u) {
        if (u != null && u.getChosenRestaurantId() != null) {
            u.setChosenRestaurant(restaurantRepository.findById(u.getChosenRestaurantId()));
        }
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
                    Timestamp ts = rs.getTimestamp("last_voted");
                    LocalDateTime lastVoted = ts != null ? ts.toLocalDateTime() : null;
                    User newUser = new User(id, name, surname, password, registered, lastVoted, enabled, EnumSet.of(role));
                    int restaurantId = rs.getInt("restaurant_id");
                    if (restaurantId > 0) {
                        newUser.setChosenRestaurant(restaurantRepository.findById(restaurantId));
                    }
                    userMap.put(id, newUser);
                }
            }

            return userMap.values().stream().collect(Collectors.toList());
        }
    }
}
