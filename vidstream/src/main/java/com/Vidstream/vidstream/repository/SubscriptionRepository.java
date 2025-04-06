package com.Vidstream.vidstream.repository;

import com.Vidstream.vidstream.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class SubscriptionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void save(Subscription model) throws Exception {
        String sql = "INSERT INTO subscription(user_id, plan_type, end_date)" +
                "VALUES" +
                "(?, ? ::subscribe, ?)";

        jdbcTemplate.update(sql, model.getUser_id(), model.getPlan().name(), model.getEndDate());
    }


}
