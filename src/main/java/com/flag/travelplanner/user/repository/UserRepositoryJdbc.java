package com.flag.travelplanner.user.repository;

import com.flag.travelplanner.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static java.sql.Types.*;

@Repository
public class UserRepositoryJdbc implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from users", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int create(User user) {
        String sqlQuery = "insert ignore into users (username, password, firstName, lastName, email) " +
                "values (?, ?, ?, ?, ?)";
        String authQuery = "insert into authorities (username, authority) values (?, ?)";
        int row = 0;
        try {
            row = jdbcTemplate.update(sqlQuery,
                    user.getUsername(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail());
            jdbcTemplate.update(authQuery,
                    user.getUsername(),
                    "ROLE_USER");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return row;
    }


    @Override
    public User findByUsername(String username) {
        String sqlQuery = "select * from users where username = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, new Object[]{username}, new int[]{VARCHAR},
                    (rs, rowNum)-> new User(rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getBoolean("enabled"),
                            rs.getDate("createTime")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String sqlQuery = "select * from users where email = ?";

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, new Object[]{email}, new int[]{VARCHAR},
                    (rs, rowNum)-> new User(rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getBoolean("enabled"),
                            rs.getDate("createTime")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int update(User user) {
        String sqlQuery ="update users set password = ?, firstName = ?, lastName = ?, email = ? where userName = ?";

        return jdbcTemplate.update(sqlQuery, user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }

    @Override
    public int deleteByUsername(String username) {
        return jdbcTemplate.update("delete from users where username = ?", username);
    }
}
