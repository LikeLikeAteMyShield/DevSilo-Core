package com.devsilo.domain;

public class Video {

    private long id;
    private String name;
    private String filename;

    public Video(long id, String name, String filename) {
        this.id = id;
        this.name = name;
        this.filename = filename;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFilename() {
        return this.filename;
    }
}
