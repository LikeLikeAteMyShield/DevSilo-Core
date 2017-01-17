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

public class DailyMotionServiceClient implements ExternalServiceClient {

    public List<ExternalVideo> query(String searchPhrase) {

        List<ExternalVideo> results = new ArrayList<ExternalVideo>();

        try {
            HttpClient client = new DefaultHttpClient();
            String url = "https://api.dailymotion.com/videos?fields=id%2Cthumbnail_url%2Ctitle%2Cowner.screenname&search=" + searchPhrase + "&limit=25";
            String requestUrl = url.replaceAll(" ","%20");
            HttpGet request = new HttpGet(requestUrl);
            String responseString = "";

            HttpResponse response = client.execute(request);
            InputStream content = response.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                responseString += s;
            }
            JSONObject json = new JSONObject(responseString);
            JSONArray list = json.getJSONArray("list");

            for (int i=0 ; i<list.length() ; i++) {
                JSONObject videoObject = list.getJSONObject(i);
                String uri = videoObject.getString("id");
                String title = videoObject.getString("title");
                String author = videoObject.getString("owner.screenname");
                String thumbnail = videoObject.getString("thumbnail_url");

                ExternalVideo video = new ExternalVideo(uri, VideoSource.DAILYMOTION, title,"", author, thumbnail);
                results.add(video);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
