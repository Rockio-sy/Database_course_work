package com.Vidstream.vidstream.dto;

import jakarta.validation.constraints.NotNull;

public class WatchListDTO {
    private Long id;
    @NotNull(message = "Video id is required")
    private Long videoId;
    @NotNull(message = "User id is required")
    private Long userId;

    public WatchListDTO(Long id, Long videoId, Long userId) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
    }

    public WatchListDTO() {
    }

    public WatchListDTO(Long videoId, Long userId) {
        this.videoId = videoId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
