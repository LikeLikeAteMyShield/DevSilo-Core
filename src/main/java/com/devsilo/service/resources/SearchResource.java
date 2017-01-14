package com.devsilo.service.resources;


import com.devsilo.api.SearchResponse;
import com.devsilo.domain.ExternalVideo;
import com.devsilo.domain.Video;
import com.devsilo.integration.ClientManager;
import com.devsilo.integration.DailyMotionServiceClient;
import com.devsilo.integration.VimeoServiceClient;
import com.devsilo.integration.YouTubeServiceClient;
import com.devsilo.persistence.VideoDao;
import com.devsilo.service.config.DevSiloConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final VideoDao videoDao;
    private DevSiloConfiguration configuration;

    public SearchResource(VideoDao videoDao, DevSiloConfiguration configuration) {
        this.videoDao = videoDao;
        this.configuration = configuration;
    }

    @GET
    public Response searchVideos(@QueryParam("searchPhrase") String searchPhrase) {

        List<Video> localVideos = videoDao.findVideosByName(searchPhrase);
        List<ExternalVideo> externalVideos = ClientManager.getInstance(configuration).getExternalVideos(searchPhrase);

        SearchResponse response = new SearchResponse(localVideos, externalVideos);

        return Response.ok(response).build();
    }
}
