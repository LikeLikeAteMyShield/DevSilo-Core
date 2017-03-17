package com.devsilo.persistence;

import com.devsilo.domain.User;
import com.devsilo.service.config.DevSiloConfiguration;
import com.mongodb.*;

import javax.ws.rs.core.NoContentException;
import java.util.UUID;

public class UserDao {

    private MongoClient client;
    private DevSiloConfiguration configuration;
    private DB db;

    public UserDao(MongoClient client, DevSiloConfiguration configuration) {
        this.client = client;
        this.configuration = configuration;
        this.db = client.getDB("devsilo");
    }

    public void addUser(User user) {

        DBCollection col = db.getCollection("users");

        BasicDBObject userObject = new BasicDBObject();
        userObject.put("_id", user.getId().toString());
        userObject.put("screenName", user.getScreenName());

        col.insert(userObject);
    }

    public User getUserById(String id) throws NoContentException {

        DBCollection col = db.getCollection("users");
        DBObject query = new BasicDBObject("_id", id);
        DBCursor cursor = col.find(query);

        User user;

        if(cursor.hasNext()) {
            user = mapUser((BasicDBObject) cursor.next());
        } else {
            throw new NoContentException("user not found");
        }

        return user;
    }

    private User mapUser(BasicDBObject object) {

        UUID id = UUID.fromString(object.getString("_id"));
        String screenName = object.getString("screenName");

        return new User(id, screenName);
    }
}
