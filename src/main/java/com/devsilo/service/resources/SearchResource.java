package com.devsilo.service.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    @GET
    public Response searchVideos(@QueryParam("searchPhrase") String searchPhrase) {

        return Response.ok(searchPhrase).build();
    }
}
