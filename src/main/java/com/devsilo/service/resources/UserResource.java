package com.devsilo.service.resources;

import com.devsilo.api.CreateUserRequest;
import com.devsilo.api.CreateUserResponse;
import com.devsilo.domain.User;
import com.devsilo.persistence.UserDao;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("users")
public class UserResource {

    private final UserDao userDao;

    public UserResource(UserDao userDao) {

        this.userDao = userDao;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response createUser(CreateUserRequest request) {

        UUID userId = UUID.randomUUID();
        User user = new User(userId, request.getScreenName());

        userDao.addUser(user);

        CreateUserResponse response = new CreateUserResponse(userId);

        return Response.ok(response).build();
    }
}
