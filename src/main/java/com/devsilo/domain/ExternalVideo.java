package com.devsilo.domain;

public class ExternalVideo {

    private String uri;
    private VideoSource source;
    private String title;
    private String description;
    private String author;
    private String thumbnailLink;


    public ExternalVideo(String uri, VideoSource source, String title, String description, String author, String thumbnailLink) {
        this.uri = uri;
        this.source = source;
        this.title = title;
        this.description = description;
        this.author = author;
        this.thumbnailLink = thumbnailLink;
    }

    public String getUri() {
        return uri;
    }

    public VideoSource getSource() { return source; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() { return author; }

    public String getThumbnailLink() {
        return thumbnailLink;
    }
}
