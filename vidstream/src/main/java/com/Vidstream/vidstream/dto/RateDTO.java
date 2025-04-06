package com.Vidstream.vidstream.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RateDTO {
    private Long id;
    @NotNull(message = "Video id required")
    private Long videoId;
    @NotNull(message = "User id required")
    private Long  userId;
    @NotNull(message = "Rate value required")
    private int rateValue;
    @NotBlank(message = "Comment required")
    private String comments;

    public RateDTO(){}
    public RateDTO(Long videoId, Long userId, int rateValue, String comments) {
        this.videoId = videoId;
        this.userId = userId;
        this.rateValue = rateValue;
        this.comments = comments;
    }

    public RateDTO(Long id, Long videoId, Long userId, int rateValue, String comments) {
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
}
