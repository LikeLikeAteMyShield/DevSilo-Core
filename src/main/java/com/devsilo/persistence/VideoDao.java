package com.devsilo.persistence;

import com.devsilo.domain.Id;
import com.devsilo.domain.Video;
import com.mongodb.*;
import javax.ws.rs.core.NoContentException;
import java.util.ArrayList;
import java.util.List;

public class VideoDao {

    private MongoClient client;
    private DB db;

    public VideoDao(MongoClient client){
        this.client = client;
        this.db = client.getDB("test");
    }

    public List<Video> getAllVideos() {

        List<Video> videos = new ArrayList<Video>();

        DBCollection col = db.getCollection("videos");
        DBCursor cursor = col.find();

        while(cursor.hasNext()) {
            BasicDBObject video = (BasicDBObject) cursor.next();
            Id id = null;
            try {
                id = new Id(video.getLong("_id"));
            } catch(Exception e){
                e.printStackTrace();
            }

            String title = video.getString("title");
            String filename = video.getString("filename");

            videos.add(new Video(id, title, filename));
        }

        return videos;
    }

    public Video getVideoById(Id id) throws NoContentException {

        DBCollection col = db.getCollection("videos");
        DBObject query = new BasicDBObject("_id", id.getValue());
        DBCursor cursor = col.find(query);
        DBObject result;

        if(cursor.hasNext()) {
            result = cursor.next();
        } else {
            throw new NoContentException("video not found");
        }

        String title = (String)result.get("title");
        String filename = (String)result.get("filename");

        Video video = new Video(id, title, filename);

        return video;
    }
}
