package com.devsilo.api;

import com.devsilo.domain.Video;

public class VideoResponse {

    private Video video;

    public VideoResponse(Video video) {
        this.video = video;
    }

    public Video getVideo() {
        return video;
    }
}
