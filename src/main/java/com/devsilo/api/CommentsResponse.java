package com.devsilo.api;

import com.devsilo.domain.Comment;

import java.util.List;

public class CommentsResponse {

    private List<Comment> comments;

    public CommentsResponse(List<Comment> comments) { this.comments = comments; }

    public List<Comment> getComments() {
        return comments;
    }
}
