package org.acme.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.MediaDTO;
import org.acme.dtos.MovieDTO;
import org.acme.dtos.SeriesDTO;
import org.acme.dtos.VideoGameDTO;
import org.acme.entities.Media;
import org.acme.entities.Movie;
import org.acme.entities.Series;
import org.acme.entities.VideoGame;
import org.acme.services.MediaService;

import java.util.List;

@Path("/{username}/media")
@ApplicationScoped
public class MediaResource {

  @Inject MediaService mediaService;

  @GET
  @Path("/wanted-media/{mediaType}")
  public List<? extends MediaDTO> getWantedMedia(
      @PathParam("username") String userName, @PathParam("mediaType") String mediaType) {
    return mediaService.getWantedMedia(userName, mediaType);
  }

  @GET
  @Path("/finished-media/{mediaType}")
  public List<? extends MediaDTO> getFinishedMedia(
      @PathParam("username") String userName, @PathParam("mediaType") String mediaType) {
    return mediaService.getFinishedMedia(userName, mediaType);
  }

  @POST
  @Path("/wanted-media")
  public Response addWantedMedia(@PathParam("username") String userName, Media media) {
    return mediaService.addMedia(userName, "#User.findExisting", media, false);
  }

  @POST
  @Path("/finished-media")
  public Response addFinishedMedia(@PathParam("username") String userName, Media media) {
    return mediaService.addMedia(userName, "#User.findExisting", media, true);
  }

  @GET
  @Path("/all-movies")
  public List<MovieDTO> getAllMovies() {
    return Movie.findAll().project(MovieDTO.class).list();
  }

  @GET
  @Path("/all-series")
  public List<SeriesDTO> getAllSeries() {
    return Series.findAll().project(SeriesDTO.class).list();
  }

  @GET
  @Path("/all-video-games")
  public List<VideoGameDTO> getAllVideoGames() {
    return VideoGame.findAll().project(VideoGameDTO.class).list();
  }
}
