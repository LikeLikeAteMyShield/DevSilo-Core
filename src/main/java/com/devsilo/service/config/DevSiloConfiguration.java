package com.devsilo.service.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class DevSiloConfiguration extends Configuration {

    @NotEmpty
    private String videoFilePath;

    @NotEmpty
    private String thumbnailFilePath;

    @JsonProperty
    public String getVideoFilePath() {
        return videoFilePath;
    }

    @JsonProperty
    public void setVideoFilePath(String filepath) {
        this.videoFilePath = filepath;
    }

    @JsonProperty
    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    @JsonProperty
    public void setThumbnailFilePath(String filePath) {
        this.thumbnailFilePath = filePath;
    }
}
