package com.devsilo.integration;

import com.devsilo.domain.ExternalVideo;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class VimeoServiceClient implements ExternalServiceClient {

    private static final String CLIENT_ID = "28b7fbc4476fd90334ef7b24e1b784cc2ca9f920";
    private static final String CLIENT_SECRET = "BQnqP+u3D7wPy5FNzprVXQhC+0zcqGLJ1rQ0BRqnyRoLBJiBAucnnVky+9sdpIKy7CREqrOnlKiU7ZJSYDGoatkfr/uOpjmMpIkyllyBXbDJlsnAhy3sso6yJLtWMHYH";

    public List<ExternalVideo> query(String searchPhrase) {

        try {
            String authString = CLIENT_ID + ":" + CLIENT_SECRET;
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("https://api.vimeo.com/oauth/authorize/client");
            request.setHeader("Authorization", "basic " + Base64.encodeBase64(authString.getBytes()).toString());
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(request);
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }
}
