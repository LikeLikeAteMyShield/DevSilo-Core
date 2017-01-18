package com.devsilo.integration;

import com.devsilo.domain.ExternalVideo;
import com.devsilo.domain.VideoSource;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VimeoServiceClient implements ExternalServiceClient {

    private final String AUTH_TOKEN;
    private static final String NUMBER_OF_VIDEOS_TO_RETURN = "25";

    public VimeoServiceClient(String authToken) {
        this.AUTH_TOKEN = authToken;
    }

    public List<ExternalVideo> query(String searchPhrase) {

        List<ExternalVideo> videos = new ArrayList<ExternalVideo>();

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("https://api.vimeo.com/videos?query=" + searchPhrase + "&per_page=" + NUMBER_OF_VIDEOS_TO_RETURN);
            request.setHeader("Authorization", "Bearer " + AUTH_TOKEN);

            HttpResponse response = client.execute(request);
            InputStream content = response.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            String responseString = "";
            while ((s = buffer.readLine()) != null) {
                responseString += s;
            }

            JSONObject json = new JSONObject(responseString);
            JSONArray responseVideoArray = json.getJSONArray("data");

            for(int i=0 ; i<responseVideoArray.length() ; i++) {
                JSONObject videoObject = responseVideoArray.getJSONObject(i);
                String uri = videoObject.getString("uri");
                String title = videoObject.getString("name");
                JSONObject authorObject = videoObject.getJSONObject("user");
                String author = authorObject.getString("name");
                JSONObject thumbnailObject = videoObject.getJSONObject("pictures");
                JSONArray thumbnailArray = thumbnailObject.getJSONArray("sizes");
                JSONObject firstThumbnailObject = thumbnailArray.getJSONObject(0);
                String thumbnail =firstThumbnailObject.getString("link");

                ExternalVideo video = new ExternalVideo(uri, VideoSource.VIMEO, title, "", author, thumbnail);
                videos.add(video);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return videos;
    }
}
