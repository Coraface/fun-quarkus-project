package org.acme.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.MovieDTO;
import org.acme.entities.Movie;
import org.acme.services.MediaService;

import java.util.List;

@Path("/{username}/media")
@ApplicationScoped
public class MediaResource {

  @Inject MediaService mediaService;

  @GET
  @Path("/movies")
  public List<MovieDTO> getWantedMovies(@PathParam("username") String userName) {
    return mediaService.getWantedMovies(userName);
  }

  @POST
  @Path("/movies")
  public Response addWantedMovies(@PathParam("username") String userName, Movie movie) {
    return mediaService.addWantedMovies(userName, movie);
  }

  @GET
  public List<MovieDTO> getAllMovies() {
    return Movie.findAll().project(MovieDTO.class).list();
  }
}
