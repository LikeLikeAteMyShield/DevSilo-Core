package com.devsilo.service;

import com.devsilo.persistence.DatabaseClient;
import com.devsilo.persistence.UserDao;
import com.devsilo.persistence.VideoDao;
import com.devsilo.service.config.DevSiloConfiguration;
import com.devsilo.service.healthchecks.DatabaseHealthCheck;
import com.devsilo.service.resources.SearchResource;
import com.devsilo.service.resources.UserResource;
import com.devsilo.service.resources.VideoResource;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

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
        System.out.println("--Now With Automated Deployment--");
        System.out.println();

        new DevSiloApplication().run(args);
    }

    public void initialize(Bootstrap<DevSiloConfiguration> bootstrap) {

    }

    public void run(DevSiloConfiguration configuration, Environment environment) {

        MongoClient client = DatabaseClient.getClient();

        VideoDao videoDao = new VideoDao(client, configuration);
        UserDao userDao = new UserDao(client, configuration);

        environment.jersey().register(MultiPartFeature.class);

        environment.jersey().register(new VideoResource(userDao, videoDao, configuration));
        environment.jersey().register(new SearchResource(videoDao, configuration));
        environment.jersey().register(new UserResource(userDao));

        environment.healthChecks().register("database", new DatabaseHealthCheck(client));
    }
}
