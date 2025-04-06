package com.Vidstream.vidstream.repository;

import com.Vidstream.vidstream.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class RateRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveRate(Rate model) throws SQLException {
        String sql = "INSERT INTO rate( video_id, user_id, rate_value, comments) " +
                "VALUES" +
                " (?, ?, ?, ?)";
        jdbcTemplate.update(sql, model.getVideoId(), model.getUserId(), model.getRateValue(), model.getComments());
    }


    public List<Rate> getRatesByVideoId(Long videoId) {
        String sql = "SELECT * FROM rate WHERE video_id = ?";
        return jdbcTemplate.query(sql, rateRowMapper, videoId);
    }

    private static final RowMapper<Rate> rateRowMapper = (rs, rowNum) -> {
        Rate rate = new Rate();
        rate.setId(rs.getLong("id"));
        rate.setRateValue(rs.getInt("rate_value"));
        rate.setComments(rs.getString("comments"));
        rate.setUserId(rs.getLong("user_id"));
        rate.setVideoId(rs.getLong("video_id"));
        return rate;
    };
}
