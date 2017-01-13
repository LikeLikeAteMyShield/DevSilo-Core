package com.devsilo.domain;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Video {

    private Id id;
    private String title;
    private String videoFilePath;
    private String thumbnailFilePath;

    public Video(Id id, String title, String videoFilePath, String thumbnailFilePath) {
        this.id = id;
        this.title = title;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Id getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVideoFilePath() {
        return this.videoFilePath;
    }

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public String getThumbNailAsBase64() {

        File img = new File(this.thumbnailFilePath);
        byte[] imgBytes;

        try {
            imgBytes = Files.readAllBytes(img.toPath());
        } catch (IOException e) {
            return "";
        }

        byte[] imgBytesAsBase64 = Base64.encodeBase64(imgBytes);
        String imgDataAsBase64 = new String(imgBytesAsBase64);

        return "data:image/jpeg;base64," + imgDataAsBase64;
    }
}
