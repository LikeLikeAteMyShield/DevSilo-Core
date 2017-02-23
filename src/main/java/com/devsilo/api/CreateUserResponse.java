package com.devsilo.api;

import java.util.UUID;

public class CreateUserResponse {

    private String createdUserId;

    public CreateUserResponse(UUID id) {
        this.createdUserId = id.toString();
    }

    public String getCreatedUserId() {
        return createdUserId;
    }
}
