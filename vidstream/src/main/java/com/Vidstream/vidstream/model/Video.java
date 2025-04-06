package com.Vidstream.vidstream.model;


public class Video {
    private Long id;
    private String path;
    private String title;
    private Long uploaderId;
    private String description;

    private String screenCap;



    public Video() {
    }

    public Video(Long id, String path, String title, Long uploader_id, String description, String screenCap) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.uploaderId = uploader_id;
        this.description = description;
        this.screenCap = screenCap;
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

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", title='" + title + '\'' +
                ", uploaderId=" + uploaderId +
                ", description='" + description + '\'' +
                ", screenCap='" + screenCap + '\'' +
                '}';
    }

    public String getScreenCap() {
        return screenCap;
    }
    public void setScreenCap(String screenCap) {
        this.screenCap = screenCap;
    }
}
