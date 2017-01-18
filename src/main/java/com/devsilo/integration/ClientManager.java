package com.devsilo.integration;

import com.devsilo.domain.ExternalVideo;
import com.devsilo.service.config.DevSiloConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientManager {

    private static ClientManager instance;
    private List<ExternalServiceClient> clients;

    private ClientManager(DevSiloConfiguration configuration) {
        YouTubeServiceClient youTubeServiceClient = new YouTubeServiceClient(configuration);
        VimeoServiceClient vimeoServiceClient = new VimeoServiceClient(configuration.getVimeoAuthToken());
        DailyMotionServiceClient dailyMotionServiceClient = new DailyMotionServiceClient();

        clients = new ArrayList<ExternalServiceClient>();
        clients.add(youTubeServiceClient);
        clients.add(vimeoServiceClient);
        clients.add(dailyMotionServiceClient);
    }

    public static ClientManager getInstance(DevSiloConfiguration configuration) {

        if(instance == null) {
            instance = new ClientManager(configuration);
        }
        return instance;
    }

    public List<ExternalVideo> getExternalVideos(String searchPhrase) {

        List<ExternalVideo> videos = new ArrayList<ExternalVideo>();

        for (ExternalServiceClient client : clients) {
            videos.addAll(client.query(searchPhrase));
        }

        Collections.shuffle(videos);

        return videos;
    }
}
