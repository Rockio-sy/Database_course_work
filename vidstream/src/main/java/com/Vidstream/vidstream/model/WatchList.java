package com.Vidstream.vidstream.model;


public class WatchList {
    private Long id;
    private Long videoId;
    private Long userId;

    public WatchList(){}
    public WatchList(Long id, Long videoId, Long userId) {
        this.id = id;
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
