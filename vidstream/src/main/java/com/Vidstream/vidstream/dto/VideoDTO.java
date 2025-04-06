package com.Vidstream.vidstream.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class VideoDTO {
    private Long id;

    @NotBlank(message = "Video path required")
    private String path;
    @NotBlank(message = "Title required")
    private String title;
    @NotNull(message = "Uploaded id required")
    @Positive(message = "Invalid uploader id")
    private Long uploader_id;
    @NotBlank(message = "Description required")
    private String description;
    private String screenCap;

    public VideoDTO() {

    }

    public String getScreenCap() {
        return screenCap;
    }

    public void setScreenCap(String screenCap) {
        this.screenCap = screenCap;
    }

    public VideoDTO(String title, Long uploader_id, String description, String screenCap) {
        this.title = title;
        this.uploader_id = uploader_id;
        this.description = description;
        this.screenCap = screenCap;
    }

    public VideoDTO(Long id, String title, Long uploader_id, String description, String screenCap) {
        this.id = id;
        this.title = title;
        this.uploader_id = uploader_id;
        this.description = description;
        this.screenCap = screenCap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUploader_id() {
        return uploader_id;
    }

    public void setUploader_id(Long uploader_id) {
        this.uploader_id = uploader_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
