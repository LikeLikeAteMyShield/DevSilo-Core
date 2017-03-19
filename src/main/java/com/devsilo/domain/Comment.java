package com.devsilo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {

    private String author;
    private String content;

    public Comment() {}

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
    }

    @JsonProperty
    public String getAuthor() {
        return author;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
