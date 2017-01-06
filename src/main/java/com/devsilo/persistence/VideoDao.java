package com.devsilo.persistence;

import com.devsilo.domain.Id;
import com.devsilo.domain.Video;

public class VideoDao {

    public VideoDao(){}

    public Video getVideoById(Id id) throws Exception {

        if (id.getValue() == 1) {
            return new Video(id.getValue(), "a", "b");
        } else {
            throw new Exception("video not found");
        }
    }
}
