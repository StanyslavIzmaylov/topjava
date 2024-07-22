package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSourceTransactionManager dataSourceTransactionManager) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.checkArguments(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("UPDATE users SET name=:name, email=:email, " +
                "password=:password, registered=:registered, " +
                "enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id ", parameterSource) == 0) {
            return null;
        }

        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.getId());

        List<Role> userRole = new ArrayList<>(user.getRoles());

        jdbcTemplate.batchUpdate("INSERT INTO user_role (role , user_id) (VALUES (?,?))", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, String.valueOf(userRole.get(i)));
                ps.setInt(2, user.getId());
            }

            @Override
            public int getBatchSize() {
                return userRole.size();
            }
        });

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        if (user == null) {
            return null;
        }
        user.setRoles(getUserRoles(id));
        return user;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        if (user == null) {
            return null;
        }
        user.setRoles(getUserRoles(user.getId()));
        return user;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT u.*, r.role as roles FROM users u LEFT JOIN user_role r ON u.id = r.user_id ORDER BY name, email",
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Map<Integer, Set<Role>> roleMap = new HashMap<>();
                        Map<Integer, User> userMap = new LinkedHashMap<>();
                        while (rs.next()) {
                            Integer userId = rs.getInt("id");
                            User user;
                            if (userMap.get(userId) != null) {
                                user = userMap.get(userId);
                            } else {
                                user = ROW_MAPPER.mapRow(rs, rs.getRow());
                            }
                            if (rs.getString("roles") != null) {
                                Role role = Role.valueOf(rs.getString("roles"));
                                roleMap.putIfAbsent(user.getId(), new HashSet<>());
                                roleMap.get(user.getId()).add(role);
                            }

                            user.setRoles(roleMap.get(user.getId()));
                            userMap.put(user.getId(), user);

                        }
                        List<User> users = new ArrayList<>(userMap.values());
                        return users;
                    }
                });
    }

    public Set<Role> getUserRoles(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM user_role WHERE user_id=?", new ResultSetExtractor<Set<Role>>() {
                    @Override
                    public Set<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Set<Role> usersRole = EnumSet.noneOf(Role.class);
                        while (rs.next()) {
                            if (rs.getString("role") != null) {
                                Role role = Role.valueOf(rs.getString("role"));
                                usersRole.add(role);
                            }
                        }
                        return usersRole;
                    }
                }, userId);
    }
}