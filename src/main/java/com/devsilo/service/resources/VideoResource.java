package com.devsilo.service.resources;

import com.devsilo.domain.Video;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("videos")
@Produces(MediaType.APPLICATION_JSON)
public class VideoResource {

    public VideoResource(ObjectMapper mapper) {}

    @GET
    public List<Video> getAllVideos() {
        return null;
    }

    @GET
    @Path("/{id}")
    public Video getVideoById(long id) {
        return null;
    }
}
