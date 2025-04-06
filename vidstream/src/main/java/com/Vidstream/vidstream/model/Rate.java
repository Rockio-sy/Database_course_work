package com.Vidstream.vidstream.model;


public class Rate {
    private Long id;
    private Long videoId;
    private Long  userId;
    private int rateValue;
    private String comments;

    public Rate() {
    }

    public Rate(Long id, Long videoId, Long userId, int rateValue, String comments) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
        this.rateValue = rateValue;
        this.comments = comments;
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

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", userId=" + userId +
                ", rateValue=" + rateValue +
                ", comments='" + comments + '\'' +
                '}';
    }
}
