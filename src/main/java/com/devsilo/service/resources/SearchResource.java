package com.devsilo.service.resources;


import com.devsilo.api.SearchResponse;
import com.devsilo.domain.ExternalVideo;
import com.devsilo.domain.Video;
import com.devsilo.persistence.VideoDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final VideoDao videoDao;

    public SearchResource(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    @GET
    public Response searchVideos(@QueryParam("searchPhrase") String searchPhrase) {

        List<Video> localVideos = videoDao.findVideosByName(searchPhrase);
        List<ExternalVideo> externalVideos = new ArrayList<ExternalVideo>();

        SearchResponse response = new SearchResponse(localVideos, externalVideos);

        return Response.ok(response).build();
    }
}
