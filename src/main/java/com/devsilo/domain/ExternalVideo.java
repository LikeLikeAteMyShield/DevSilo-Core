package com.devsilo.domain;

public class ExternalVideo {

    private String title;
    private String description;
    private String uri;
    private String thumbnailLink;


    public ExternalVideo(String title, String description, String uri, String thumbnailLink) {
        this.title = title;
        this.description = description;
        this.uri = uri;
        this.thumbnailLink = thumbnailLink;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }
}
