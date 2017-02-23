package com.devsilo.persistence;

import com.devsilo.domain.User;
import com.devsilo.service.config.DevSiloConfiguration;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

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
}
