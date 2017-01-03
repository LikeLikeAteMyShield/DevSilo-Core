package com.devsilo.service;

import com.devsilo.service.config.DevSiloConfiguration;
import com.devsilo.service.resources.MediaResource;
import com.devsilo.service.resources.VideoResource;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DevSiloApplication extends Application<DevSiloConfiguration> {

    public static void main(String[] args) throws Exception {

        System.out.println();
        System.out.println("██████╗ ███████╗██╗   ██╗███████╗██╗██╗      ██████╗");
        System.out.println("██╔══██╗██╔════╝██║   ██║██╔════╝██║██║     ██╔═══██╗");
        System.out.println("██║  ██║█████╗  ██║   ██║███████╗██║██║     ██║   ██║");
        System.out.println("██║  ██║██╔══╝  ╚██╗ ██╔╝╚════██║██║██║     ██║   ██║");
        System.out.println("██████╔╝███████╗ ╚████╔╝ ███████║██║███████╗╚██████╔╝");
        System.out.println("╚═════╝ ╚══════╝  ╚═══╝  ╚══════╝╚═╝╚══════╝ ╚═════╝");
        System.out.println();

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB("test");
            System.out.println("Connected to DB successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        new DevSiloApplication().run(args);
    }

    public void initialize(Bootstrap<DevSiloConfiguration> bootstrap) {

    }

    public void run(DevSiloConfiguration configuration, Environment environment) {

        environment.jersey().register(new MediaResource());
        environment.jersey().register(new VideoResource());
    }
}
