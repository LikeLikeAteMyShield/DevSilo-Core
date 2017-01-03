package com.devsilo.api;

import com.devsilo.domain.Video;

import java.util.List;

public class VideoListResponse {

    private List<Video> videos;

    public VideoListResponse(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }
}
