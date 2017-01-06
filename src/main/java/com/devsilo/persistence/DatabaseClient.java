package com.devsilo.persistence;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;

public class DatabaseClient {

    private static DatabaseClient instance;
    private MongoClient client;

    private DatabaseClient(MongoClient client) {
        this.client = client;
    }

    private static DatabaseClient getInstance() {

        if (instance == null) {
            try {
                instance = new DatabaseClient(new MongoClient("localhost", 27017));
            } catch(UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static MongoClient getClient() {
        return getInstance().client;
    }
}
