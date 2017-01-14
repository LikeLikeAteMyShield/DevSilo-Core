package com.devsilo.service.resources;

import com.devsilo.api.VideoListResponse;
import com.devsilo.api.VideoResponse;
import com.devsilo.domain.Id;
import com.devsilo.domain.Video;
import com.devsilo.persistence.VideoDao;
import com.devsilo.streamng.StreamingService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Path("videos")
public class VideoResource {

    private final VideoDao videoDao;

    public VideoResource(VideoDao videoDao) {

        this.videoDao = videoDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getAllVideos() {

        List<Video> videos = videoDao.getAllVideos();
        VideoListResponse response = new VideoListResponse(videos);

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces("video/mp4")
    public Response streamVideo(@PathParam("id") long untrusted_id, @HeaderParam("Range") String range) throws Exception {

        Video video;

        try {
            video = getVideo(untrusted_id);
        } catch(Exception e) {
            return getResponseForException(e);
        }

        File asset = new File(video.theVideoFilePath());
        return StreamingService.buildStream(asset, range);
    }

    @GET
    @Path("/{id}/info")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getVideoInfo(@PathParam("id") long untrusted_id) {

        Video video;

        try {
            video = getVideo(untrusted_id);
        } catch(Exception e) {
            return getResponseForException(e);
        }

        VideoResponse response = new VideoResponse(video);
        return Response.ok(response).build();
    }

    private Video getVideo(long untrusted_id) throws Exception {

        Id id;
        Video video;

        try {
            id = new Id(untrusted_id);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        try {
            video = videoDao.getVideoById(id);
        } catch (NoContentException e) {
            throw e;
        }

        return video;
    }

    private Response getResponseForException(Exception e) {
        if(e instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if(e instanceof NoContentException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
