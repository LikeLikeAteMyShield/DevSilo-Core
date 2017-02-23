package com.devsilo.api;

import javax.validation.constraints.NotNull;

public class CreateUserRequest {

    @NotNull
    private String screenName;

    public String getScreenName() {
        return this.screenName;
    }
}
