package com.devsilo.service.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DatabaseHealthCheck extends HealthCheck {

    private MongoClient client;

    public DatabaseHealthCheck(MongoClient client) {
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {

        try {
            DB db = client.getDB("devsilo");
            Boolean canConnect = db.collectionExists("videos");
        } catch(Exception e) {
            return Result.unhealthy("Cannot connect to DB");
        }
        return Result.healthy();
    }
}
