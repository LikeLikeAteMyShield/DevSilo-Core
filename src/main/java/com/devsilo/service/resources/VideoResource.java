package com.devsilo.service.resources;

import com.devsilo.api.VideoListResponse;
import com.devsilo.api.VideoResponse;
import com.devsilo.domain.Id;
import com.devsilo.domain.User;
import com.devsilo.domain.Video;
import com.devsilo.persistence.UserDao;
import com.devsilo.persistence.VideoDao;
import com.devsilo.service.config.DevSiloConfiguration;
import com.devsilo.streamng.StreamingService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Random;

@Path("videos")
public class VideoResource {

    private final UserDao userDao;
    private final VideoDao videoDao;
    private final DevSiloConfiguration configuration;

    public VideoResource(UserDao userDao, VideoDao videoDao, DevSiloConfiguration configuration) {

        this.userDao = userDao;
        this.videoDao = videoDao;
        this.configuration = configuration;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getAllVideos() {

        List<Video> videos = videoDao.getAllVideos();
        VideoListResponse response = new VideoListResponse(videos);

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces("video/mp4")
    public Response streamVideo(@PathParam("id") long untrusted_id, @HeaderParam("Range") String range) throws Exception {

        Video video;

        try {
            video = getVideo(untrusted_id);
        } catch(Exception e) {
            return getResponseForException(e);
        }

        File asset = new File(video.theVideoFilePath());
        return StreamingService.buildStream(asset, range);
    }

    @GET
    @Path("/{id}/info")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getVideoInfo(@PathParam("id") long untrusted_id) {

        Video video;

        try {
            video = getVideo(untrusted_id);
        } catch(Exception e) {
            return getResponseForException(e);
        }

        VideoResponse response = new VideoResponse(video);
        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadVideo(@HeaderParam("user-id") String userId,
                                @HeaderParam("video-meta") String videoMetaJsonString,
                                @FormDataParam("file")InputStream uploadedInputStream,
                                @FormDataParam("file")FormDataContentDisposition fileDetail,
                                @FormDataParam("thumbnail")InputStream thumbnailInputStream,
                                @FormDataParam("thumbnail")FormDataContentDisposition thumbnailDetail) {
        User user;

        try {
            user = userDao.getUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        JSONObject videoMetaJson = new JSONObject(videoMetaJsonString);

        Id id;

        long lowerBoundary = 1L;
        long upperBoundary = 1000000L;

        Random RNG = new Random();
        long idValue = lowerBoundary + ((long) (RNG.nextDouble() * (upperBoundary - lowerBoundary)));

        try {
            id = new Id(idValue);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        String title = videoMetaJson.getString("title");
        String videoFilePath = fileDetail.getFileName();
        String thumbnailFilename = thumbnailDetail.getFileName();

        Video video = new Video(id, title, user.getScreenName(), videoFilePath, thumbnailFilename);
        videoDao.addVideo(video);

        String uploadedFileLocation = configuration.getVideoFilePath() + fileDetail.getFileName();
        String thumbnailLocation = configuration.getThumbnailFilePath() + thumbnailDetail.getFileName();

        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded successfully";

        writeToFile(thumbnailInputStream, thumbnailLocation);
        output = output + " - Thumbnail created";

        return Response.status(200).entity(output).build();
    }

    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private Video getVideo(long untrusted_id) throws Exception {

        Id id;
        Video video;

        try {
            id = new Id(untrusted_id);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        try {
            video = videoDao.getVideoById(id);
        } catch (NoContentException e) {
            throw e;
        }

        return video;
    }

    private Response getResponseForException(Exception e) {
        if(e instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if(e instanceof NoContentException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
