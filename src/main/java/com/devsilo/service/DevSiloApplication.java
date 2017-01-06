package com.devsilo.service;

import com.devsilo.persistence.DatabaseClient;
import com.devsilo.persistence.VideoDao;
import com.devsilo.service.config.DevSiloConfiguration;
import com.devsilo.service.resources.MediaResource;
import com.devsilo.service.resources.VideoResource;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DevSiloApplication extends Application<DevSiloConfiguration> {

    public static void main(String[] args) throws Exception {

        System.out.println("\u001B[33m");
        System.out.println("██████╗ ███████╗██╗   ██╗███████╗██╗██╗      ██████╗");
        System.out.println("██╔══██╗██╔════╝██║   ██║██╔════╝██║██║     ██╔═══██╗");
        System.out.println("██║  ██║█████╗  ██║   ██║███████╗██║██║     ██║   ██║");
        System.out.println("██║  ██║██╔══╝  ╚██╗ ██╔╝╚════██║██║██║     ██║   ██║");
        System.out.println("██████╔╝███████╗ ╚████╔╝ ███████║██║███████╗╚██████╔╝");
        System.out.println("╚═════╝ ╚══════╝  ╚═══╝  ╚══════╝╚═╝╚══════╝ ╚═════╝");
        System.out.println();

        new DevSiloApplication().run(args);
    }

    public void initialize(Bootstrap<DevSiloConfiguration> bootstrap) {

    }

    public void run(DevSiloConfiguration configuration, Environment environment) {

        MongoClient client = DatabaseClient.getClient();

        VideoDao videoDao = new VideoDao(client);

        environment.jersey().register(new MediaResource(configuration.getVideoFilePath()));
        environment.jersey().register(new VideoResource(videoDao));
    }
}
