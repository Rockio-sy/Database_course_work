package com.Vidstream.vidstream.repository;

import com.Vidstream.vidstream.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class UserRepository {
    private static final RowMapper<Users> userRowMapper = (rs, rowNum) -> {
        Users user = new Users();
        user.setId(rs.getLong("id"));
        user.setFullName(rs.getString("full_name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        return user;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Users isExistsByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }

    public Long isExistsByEmail(String email) throws SQLException {
        String sql = "SELECT id FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }

    public Long createNewUser(Users user) throws SQLException {
        String sql = "INSERT INTO users(full_name, username, email, password) VALUES " +
                "(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void setUserPlan(Long userId, String plan){
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        jdbcTemplate.update(sql, plan, userId);
    }
}
