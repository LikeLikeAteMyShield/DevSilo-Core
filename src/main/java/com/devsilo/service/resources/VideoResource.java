package com.devsilo.service.resources;

import com.devsilo.api.VideoListResponse;
import com.devsilo.api.VideoResponse;
import com.devsilo.domain.Video;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("videos")
@Produces(MediaType.APPLICATION_JSON)
public class VideoResource {

    public VideoResource() {}

    @GET
    public Response getAllVideos() {

        Video v1 = new Video(1, "a", "b");
        Video v2 = new Video(2, "c", "d");
        List<Video> videos = new ArrayList<Video>();
        videos.add(v1);
        videos.add(v2);

        VideoListResponse response = new VideoListResponse(videos);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getVideoById(@PathParam("id") long id) {

        Video video = new Video(id, "a", "b");
        VideoResponse response = new VideoResponse(video);
        return Response.ok(response).build();
    }
}
