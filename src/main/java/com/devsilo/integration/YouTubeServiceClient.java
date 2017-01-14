package com.devsilo.integration;

import com.devsilo.domain.ExternalVideo;
import com.devsilo.domain.VideoSource;
import com.devsilo.service.config.DevSiloConfiguration;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YouTubeServiceClient implements ExternalServiceClient {

    private static DevSiloConfiguration configuration;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youTube;

    public YouTubeServiceClient(DevSiloConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<ExternalVideo> query(String searchPhrase) {

        List<ExternalVideo> results = new ArrayList<ExternalVideo>();

        try {
            youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) throws IOException {

                }
            }).setApplicationName("devsilo").build();

            YouTube.Search.List search = youTube.search().list("id,snippet");

            String apiKey = configuration.getYouTubeApiKey();
            search.setKey(apiKey);
            search.setQ(searchPhrase);

            search.setType("video");

            search.setFields("items(id/videoId,snippet/title,snippet/description,snippet/channelTitle,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchListResponse = search.execute();
            //System.out.println(searchListResponse);
            List<SearchResult> searchResultList = searchListResponse.getItems();

            results = parse(searchResultList.iterator());

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return results;
    }

    private static List<ExternalVideo> parse(Iterator<SearchResult> searchResultIterator) {

        List<ExternalVideo> videos = new ArrayList<ExternalVideo>();

        while (searchResultIterator.hasNext()) {
            SearchResult resultVideo = searchResultIterator.next();
            System.out.println(resultVideo);
            ResourceId rId = resultVideo.getId();
            Thumbnail thumbnail = resultVideo.getSnippet().getThumbnails().getDefault();

            ExternalVideo video = new ExternalVideo(rId.getVideoId(), VideoSource.YOUTUBE, resultVideo.getSnippet().getTitle(), resultVideo.getSnippet().getDescription(), resultVideo.getSnippet().getChannelTitle(), thumbnail.getUrl());
            videos.add(video);
        }

        return videos;
    }
}
