package com.devsilo.domain;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Video {

    private Id id;
    private String title;
    private String author;
    private String videoFilePath;
    private String thumbnailFilePath;
    private String thumbnailData;

    public Video(Id id, String title, String author, String videoFilePath, String thumbnailFilePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Id getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() { return this.author; }

    public String theVideoFilePath() {
        return this.videoFilePath;
    }

    public String theThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public String getThumbnailData() {

        File image = new File(thumbnailFilePath);
        byte[] imageBytes = new byte[(int)image.length()];

        try {
            FileInputStream inputStream = new FileInputStream(image);
            inputStream.read(imageBytes);
        } catch(FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("Error Reading File");
            e.printStackTrace();
        }

        byte[] imageBytesBase64 = Base64.encodeBase64(imageBytes);
        String imageDataAsBase64 = new String(imageBytesBase64);

        return imageDataAsBase64;
    }
}
