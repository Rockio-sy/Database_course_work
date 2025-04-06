package com.Vidstream.vidstream.repository;

import com.Vidstream.vidstream.dto.VideoDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class VideoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean saveTempFile(String path, Long userId) {
        String sql = "INSERT INTO video (title, path, uploader_id, description, screen_cap) VALUES (?, ?, ?, ?, ?)";
        int rowAffected = jdbcTemplate.update(sql, "TEMP", path, userId, "TEMP", "TEMP");
        return rowAffected > 0;
    }

    public Long isExistTempPath(Long userId) {
        String sql = "SELECT id FROM video WHERE uploader_id = ? AND title = 'TEMP'";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void cleanUserTemp(Long userId) {
        String sql = "DELETE FROM video WHERE uploader_id = ? and title = ?";
        jdbcTemplate.update(sql, userId, "TEMP");
    }

    public boolean savePost(VideoDTO videoDTO, String newPath) throws SQLException {
        // SQL update query
        String sql = "UPDATE video SET title = ?, path = ?, description = ?, screen_cap = ? WHERE path = ? AND uploader_id = ?";


        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    videoDTO.getTitle(),
                    newPath,
                    videoDTO.getDescription(),
                    videoDTO.getScreenCap(),
                    videoDTO.getPath(),
                    videoDTO.getUploader_id());

            // If one row is affected, it means the update was successful
            return rowsAffected > 0;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new CustomException("Video already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Long getVideoId(String newPath, Long uploaderId) {
        // Assuming you have a column called `id` in the `video` table
        String selectSql = "SELECT id FROM video WHERE path = ? AND uploader_id = ?";
        Optional<Long> returnedId = Optional.of(jdbcTemplate.queryForObject(selectSql, Long.class, newPath, uploaderId));
        return returnedId.orElseThrow(() -> new CustomException("File updated, but not found", HttpStatus.INTERNAL_SERVER_ERROR));

    }

    public List<Video> getAllVideos() {
        String sql = "SELECT * FROM video";
        return jdbcTemplate.query(sql, videoRowMapper);
    }

    public List<Video> getByTitle(String title) {
        String sql = "SELECT * FROM video WHERE title LIKE ?";
        return jdbcTemplate.query(sql, videoRowMapper, '%' + title + '%');
    }


    private static final RowMapper<Video> videoRowMapper = (rs, rowNum) -> {
        Video video = new Video();
        video.setId(rs.getLong("id"));
        video.setTitle(rs.getString("title"));
        video.setPath(rs.getString("path"));
        video.setDescription(rs.getString("description"));
        video.setUploaderId(rs.getLong("uploader_id"));
        video.setScreenCap(rs.getString("screen_cap"));
        return video;
    };

    public Optional<Video> getById(Long id) {
        String sql = "SELECT * FROM video WHERE id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, videoRowMapper, id));
    }
}
