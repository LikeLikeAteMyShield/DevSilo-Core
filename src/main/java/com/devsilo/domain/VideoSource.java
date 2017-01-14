package com.devsilo.domain;

public enum VideoSource {

    DAILYMOTION("dailymotion"),
    YOUTUBE("youtube"),
    VIMEO("vimeo");

    private String name;

    VideoSource(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static VideoSource fromString(String text) {

        if(text != null) {
            for(VideoSource videoSource: VideoSource.values()) {
                if(text.equalsIgnoreCase(videoSource.getName())) {
                    return videoSource;
                }
            }
        }

        throw new IllegalArgumentException("No video source with name " + text + " found");
    }
}
