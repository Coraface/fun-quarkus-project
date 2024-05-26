package org.acme.services;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.acme.dtos.MovieDTO;
import org.acme.entities.Movie;
import org.acme.entities.User;

@ApplicationScoped
public class MediaService {

  public List<MovieDTO> getWantedMovies(String userName) {
    return Movie.find("#Movie.findWantedMovies", Parameters.with("userName", userName))
        .project(MovieDTO.class)
        .list();
  }

  @Transactional
  public Response addWantedMovies(String userName, Movie movie) {
    User user = User.find("#User.findExistingWantedMovies", Parameters.with("userName", userName)).firstResult();
//    Optional<MovieDTO> movieDTOOptional =
//        Movie.find(
//                "#Movie.findExistingWantedMovies",
//                Parameters.with("title", movie.getTitle())
//                    .and("year", movie.getYear())
//                    .and("userName", userName))
//            .project(MovieDTO.class)
//            .firstResultOptional();
//    if (movieDTOOptional.isPresent()) {
//      return Response.status(Response.Status.CONFLICT).entity("Movie already exists.").build();
//    }
    Set<Movie> wantedMovies = user.getWantedMoviesList();
    if (wantedMovies.contains(movie)) {
      return Response.status(Response.Status.CONFLICT).entity("Movie already exists.").build();
    }

    // Establish the relationship between the user and the movie
    wantedMovies.add(movie);
    movie.getUsersThatWant().add(user);
    return Response.ok().entity("Movie added to wanted list.").build();
  }
}
