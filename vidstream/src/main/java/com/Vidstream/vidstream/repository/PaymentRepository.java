package com.Vidstream.vidstream.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PaymentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(BigDecimal amount, Long userId){
        String sql = "INSERT INTO payment (amount, user_id)" +
                "VALUES" +
                "(? ,?)";
        jdbcTemplate.update(sql, amount, userId);
    }
}
