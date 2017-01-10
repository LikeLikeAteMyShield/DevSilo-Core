package com.devsilo.api;

import com.devsilo.domain.ExternalVideo;
import com.devsilo.domain.Video;

import java.util.List;

public class SearchResponse {

    private List<Video> localVideos;
    private List<ExternalVideo> externalVideos;

    public SearchResponse(List<Video> localVideos, List<ExternalVideo> externalVideos) {
        this.localVideos = localVideos;
        this.externalVideos = externalVideos;
    }

    public List<Video> getLocalVideos() {
        return localVideos;
    }

    public List<ExternalVideo> getExternalVideos() {
        return externalVideos;
    }
}
