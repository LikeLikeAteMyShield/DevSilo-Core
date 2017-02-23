package com.devsilo.domain;

import java.util.UUID;

public class User {

    private UUID id;
    private String screenName;

    public User(UUID id, String screenName) {
        this.id = id;
        this.screenName = screenName;
    }

    public UUID getId() {
        return this.id;
    }

    public String getScreenName() {
        return this.screenName;
    }
}
