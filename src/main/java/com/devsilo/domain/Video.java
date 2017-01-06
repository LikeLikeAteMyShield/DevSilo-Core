package com.devsilo.domain;

public class Video {

    private Id id;
    private String title;
    private String filename;

    public Video(Id id, String title, String filename) {
        this.id = id;
        this.title = title;
        this.filename = filename;
    }

    public Id getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFilename() {
        return this.filename;
    }
}
