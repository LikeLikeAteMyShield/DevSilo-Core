package com.devsilo.persistence;

import com.devsilo.domain.*;
import com.devsilo.service.config.DevSiloConfiguration;
import com.mongodb.*;
import javax.ws.rs.core.NoContentException;
import java.util.ArrayList;
import java.util.List;

public class VideoDao {

    private MongoClient client;
    private DevSiloConfiguration configuration;
    private DB db;

    public VideoDao(MongoClient client, DevSiloConfiguration configuration){
        this.client = client;
        this.configuration = configuration;
        this.db = client.getDB("devsilo");
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
        DBCursor cursor = col.find(new BasicDBObject("$text", new BasicDBObject("$search", searchPhrase)));

        while(cursor.hasNext()) {
            Video video = mapVideo((BasicDBObject)cursor.next());
            videos.add(video);
        }

        return videos;
    }

    public void addVideo(Video video) {

        DBCollection col = db.getCollection("videos");

        BasicDBObject videoObject = new BasicDBObject();
        videoObject.put("_id", video.getId().getValue());
        videoObject.put("title", video.getTitle());
        videoObject.put("author", video.getAuthor());
        videoObject.put("filename", video.theVideoFilePath());
        videoObject.put("imageName", video.theThumbnailFilePath());

        col.insert(videoObject);
    }

    public List<Comment> getCommentsForVideo(Video video) {

        List<Comment> comments = new ArrayList<Comment>();

        DBCollection col = db.getCollection("videos");

        DBObject query = new BasicDBObject("_id", video.getId().getValue());
        DBCursor cursor = col.find(query);

        BasicDBObject object = new BasicDBObject();

        while(cursor.hasNext()) {
            object = (BasicDBObject) cursor.next();
        }

        List<BasicDBObject> commentObjects = (List<BasicDBObject>) object.get("comments");

        if (commentObjects != null) {
            for (BasicDBObject commentObject : commentObjects) {
                Comment comment = new Comment(commentObject.getString("author"), commentObject.getString("content"));
                comments.add(comment);
            }
        }

        return comments;
    }

    public void addCommentForVideo(Video video, String author, String content) {

        DBCollection col = db.getCollection("videos");

        DBObject searchObject = new BasicDBObject("_id", video.getId().getValue());
        DBObject modifiedObject = new BasicDBObject();

        BasicDBObject commentObject = new BasicDBObject().append("author", author).append("content", content);
        modifiedObject.put("$push", new BasicDBObject().append("comments", commentObject));

        col.update(searchObject, modifiedObject);
    }

    private Video mapVideo(BasicDBObject object) {

        Id id = null;
        try {
            id = new Id(object.getLong("_id"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        String title = object.getString("title");
        String author = object.getString("author");
        String filePath = configuration.getVideoFilePath() + object.getString("filename");
        String thumbnailPath = configuration.getThumbnailFilePath() + object.getString("imageName");

        return new Video(id, title, author, filePath, thumbnailPath);
    }
}
