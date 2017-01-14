package com.devsilo.service.resources;


import com.devsilo.api.SearchResponse;
import com.devsilo.domain.ExternalVideo;
import com.devsilo.domain.Video;
import com.devsilo.domain.VideoSource;
import com.devsilo.integration.YouTubeServiceClient;
import com.devsilo.persistence.VideoDao;
import com.devsilo.service.config.DevSiloConfiguration;

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
    private DevSiloConfiguration conf;

    public SearchResource(VideoDao videoDao, DevSiloConfiguration conf) {
        this.videoDao = videoDao;
        this.conf = conf;
    }

    @GET
    public Response searchVideos(@QueryParam("searchPhrase") String searchPhrase) {

        YouTubeServiceClient client = new YouTubeServiceClient(conf);

        List<Video> localVideos = videoDao.findVideosByName(searchPhrase);
        List<ExternalVideo> externalVideos = client.query(searchPhrase);

        SearchResponse response = new SearchResponse(localVideos, externalVideos);

        return Response.ok(response).build();
    }
}
