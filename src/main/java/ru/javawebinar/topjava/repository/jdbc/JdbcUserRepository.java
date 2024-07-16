package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        ValidationUtil.getParametr(user);
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
        List<User> users = jdbcTemplate.query("SELECT u.*, r.role as roles  FROM users u LEFT JOIN user_role r ON u.id = r.user_id WHERE id=?", ROW_MAPPER, id);
        if (users.isEmpty()) {
            return null;
        }
        User user = users.getFirst();
        user.setRoles(getUserRoles(id));
        return user;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT u.*, r.role as roles  FROM users u LEFT JOIN user_role r ON u.id = r.user_id WHERE u.email=?",
                ROW_MAPPER, email);
        User user = users.getFirst();
        user.setRoles(getUserRoles(user.getId()));
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        for (User user : users) {
            user.setRoles(getUserRoles(user.getId()));
        }
        return users;
    }

    public Set<Role> getUserRoles(Integer userId) {
        List<User> users = jdbcTemplate.query(
                "SELECT u.*, r.role as roles  FROM users u LEFT JOIN user_role r ON u.id = r.user_id WHERE user_id =?", ROW_MAPPER, userId);

        Set<Role> userRole = new HashSet<>();
        for (User user : users) {
            userRole.addAll(user.getRoles());
        }
        return userRole;
    }
}