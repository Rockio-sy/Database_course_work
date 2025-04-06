package com.Vidstream.vidstream.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class WatchListRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void addVideo(Long videoId, Long userId) {
        String sql = "INSERT INTO watch_list(video_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, videoId, userId);
    }


    public List<Long> getVideosId(Long userId) {
        String sql = "SELECT video_id FROM watch_list WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }

    public boolean isExists(Long videoId, Long userId) {
        String sql = "SELECT COUNT(*) FROM watch_list WHERE user_id = ? AND video_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, videoId);
        return count > 0;
    }

    public void deleteFromWatchList(Long videoId, Long userId){
        String sql = "DELETE FROM watch_list WHERE user_id = ? AND video_id = ?";
        jdbcTemplate.update(sql, userId, videoId);
    }
}
