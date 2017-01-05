package com.devsilo.service.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class DevSiloConfiguration extends Configuration {

    @NotEmpty
    private String videoFilePath;

    @JsonProperty
    public String getVideoFilePath() {
        return videoFilePath;
    }

    @JsonProperty
    public void setVideoFilePath(String filepath) {
        this.videoFilePath = filepath;
    }
}
