package com.devsilo.domain;

public class Video {

    private Id id;
    private String title;
    private String videoFilePath;
    private String thumbnailFilePath;

    public Video(Id id, String title, String videoFilePath, String thumbnailFilePath) {
        this.id = id;
        this.title = title;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Id getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String theVideoFilePath() {
        return this.videoFilePath;
    }

    public String theThumbnailFilePath() {
        return thumbnailFilePath;
    }
}
