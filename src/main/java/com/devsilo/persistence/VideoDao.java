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
            Video video = mapVideo((BasicDBObject)cursor.next());
            videos.add(video);
        }

        return videos;
    }

    public Video getVideoById(Id id) throws NoContentException {

        DBCollection col = db.getCollection("videos");
        DBObject query = new BasicDBObject("_id", id.getValue());
        DBCursor cursor = col.find(query);
        Video video;

        if(cursor.hasNext()) {
            video = mapVideo((BasicDBObject) cursor.next());
        } else {
            throw new NoContentException("video not found");
        }

        return video;
    }

    public List<Video> findVideosByName(String searchPhrase) {

        List<Video> videos = new ArrayList<Video>();

        DBCollection col = db.getCollection("videos");
        DBObject searchQuery = new BasicDBObject("$text", new BasicDBObject("$search", searchPhrase));
        DBCursor cursor = col.find(new BasicDBObject("title", searchQuery));

        while(cursor.hasNext()) {
            Video video = mapVideo((BasicDBObject)cursor.next());
            videos.add(video);
        }

        return videos;
    }

    private Video mapVideo(BasicDBObject object) {

        Id id = null;
        try {
            id = new Id(object.getLong("_id"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        String title = object.getString("title");
        String filename = object.getString("filename");

        return new Video(id, title, filename);
    }
}
