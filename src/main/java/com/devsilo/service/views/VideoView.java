package com.devsilo.service.views;

import com.devsilo.domain.Video;
import io.dropwizard.views.View;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class VideoView extends View {

    private final Video video;

    public VideoView(Video video) {
        super("video.ftl");
        this.video = video;
    }

    public Video getVideo() {
        return video;
    }
}
