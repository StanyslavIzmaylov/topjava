package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
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
    private Validator validator;

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
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        Set<ConstraintViolation<User>> argsViolations = validator.validate(user);
        if (argsViolations.size() > 0) {
            throw new ConstraintViolationException(argsViolations);
        }

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
        Set<Role> userRole = new HashSet<>();
        if (users.isEmpty()){
            return null;
        }
        for (User user : users){
            userRole.addAll(user.getRoles());
        }
        User user = users.get(0);
        user.setRoles(userRole);
        return user;
    }

    @Override
    public User getByEmail(String email) {

//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT u.*, r.role as roles  FROM users u LEFT JOIN user_role r ON u.id = r.user_id WHERE u.email=?", ROW_MAPPER, email);
        Set<Role> userRole = new HashSet<>();

        for (User user : users){
            userRole.addAll(user.getRoles());
        }
        User user = users.get(0);
        user.setRoles(userRole);
        return user;
    }

    @Override
    public List<User> getAll() {


        return jdbcTemplate.query("SELECT u.*, r.role  FROM users u LEFT JOIN user_role r ON u.id = r.user_id ORDER BY name, email",
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        List<User> users = new ArrayList<>();
                        Map<Integer, Set<Role>> roleMap = new HashMap<>();
                        Map<Integer, User> userMap = new HashMap<>();

                        while (rs.next()) {
                            User user = new User();
                            Integer userId = rs.getInt("id");
                            user.setId(userId);
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            user.setPassword(rs.getString("password"));
                            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                            user.setEnabled(rs.getBoolean("enabled"));
                            user.setRegistered(rs.getDate("registered"));

                            if (rs.getString("role") == null) {
                                roleMap.put(userId, null);
                            } else {
                                Role role = Role.valueOf(rs.getString("role"));
                                roleMap.putIfAbsent(userId, new HashSet<>());
                                roleMap.get(userId).add(role);
                            }
                            user.setRoles(roleMap.get(userId));

                            userMap.put(userId, user);
                        }
                        users.addAll(userMap.values());
                        return users.stream()
                                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).toList();
                    }
                });
    }
}