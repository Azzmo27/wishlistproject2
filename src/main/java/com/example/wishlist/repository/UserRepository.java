package com.example.wishlist.repository;

import com.example.wishlist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    public void createUser(User user) {
        String sql = "INSERT INTO user (username, user_password, first_name, last_name, email, birthday) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, user.getUsername(), user.getUserPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday());
    }

    public boolean verifyUserLogin(String username, String userPassword) {
        String sql = "SELECT username, user_password FROM user WHERE username = ? AND user_password = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        try {
            User user = template.queryForObject(sql, rowMapper, username, userPassword);
            return user != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public int getUserId(String username, String userPassword) {
        String sql = "SELECT user_id FROM user WHERE username = ? AND user_password = ?";
        RowMapper<Integer> rowMapper = (resultSet, i) -> resultSet.getInt("user_id");
        return template.queryForObject(sql, rowMapper, username, userPassword);
    }
}
