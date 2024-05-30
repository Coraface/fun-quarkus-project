package org.acme.services;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.acme.dtos.MediaDTO;
import org.acme.dtos.MovieDTO;
import org.acme.dtos.SeriesDTO;
import org.acme.dtos.VideoGameDTO;
import org.acme.entities.*;
import org.acme.entities.Media.MediaType;

@ApplicationScoped
public class MediaService {

  public List<? extends MediaDTO> getWantedMedia(String userName, String mediaType) {
    MediaType media = MediaType.valueOf(mediaType.toUpperCase());
    return switch (media) {
      case MOVIE -> getWantedMedia(userName, Movie.class, MovieDTO.class, true);
      case SERIES -> getWantedMedia(userName, Series.class, SeriesDTO.class, false);
      case VIDEO_GAME -> getWantedMedia(userName, VideoGame.class, VideoGameDTO.class, true);
      default -> throw new IllegalArgumentException("Unknown media type: " + mediaType);
    };
  }

  private <T extends Media, D> List<D> getWantedMedia(
      String userName, Class<T> mediaClass, Class<D> dtoClass, boolean usePlural) {
    String queryName =
        "#Media.findUsersWanted" + mediaClass.getSimpleName() + (usePlural ? "s" : "");
    return Media.find(queryName, Parameters.with("userName", userName)).project(dtoClass).list();
  }

  public List<? extends MediaDTO> getFinishedMedia(String userName, String mediaType) {
    MediaType media = MediaType.valueOf(mediaType.toUpperCase());
    return switch (media) {
      case MOVIE -> getWatchedMedia(userName, Movie.class, MovieDTO.class, true);
      case SERIES -> getWatchedMedia(userName, Series.class, SeriesDTO.class, false);
      case VIDEO_GAME -> getWatchedMedia(userName, VideoGame.class, VideoGameDTO.class, true);
      default -> throw new IllegalArgumentException("Unknown media type: " + mediaType);
    };
  }

  private <T extends Media, D> List<D> getWatchedMedia(
      String userName, Class<T> mediaClass, Class<D> dtoClass, boolean usePlural) {
    String queryName =
        "#Media.findUsersWatched" + mediaClass.getSimpleName() + (usePlural ? "s" : "");
    return Media.find(queryName, Parameters.with("userName", userName)).project(dtoClass).list();
  }

  @Transactional
  public <T extends Media> Response addMedia(
      String userName, String namedQuery, T media, boolean isFinished) {
    String mediaType = media.getClass().getSimpleName();
    // Used for initializing associations -> 1 query only, but warning for memory usage
    // left in case of change of mind
    //    String query = namedQuery + mediaType + (!mediaType.equals(Media.MediaType.SERIES.name())
    // ? "s" : "");
    //    User user = User.find(query, Parameters.with("userName", userName)).singleResult();
    User user = User.find("userName", userName).singleResult();
    Set<T> targetList =
        !isFinished ? user.getWantedMediaList(media) : user.getFinishedMediaList(media);
    if (targetList.contains(media)) {
      return Response.status(Response.Status.CONFLICT)
          .entity(mediaType + " already exists.")
          .build();
    }

    // Establish the relationship between the user and the movie
    Optional<T> mediaOptional =
        T.find(
                "#Media.findExisting" + mediaType,
                Parameters.with("title", media.getTitle()).and("year", media.getYear()))
            .singleResultOptional();
    if (!isFinished) {
      addWantedMedia(media, mediaOptional, user, targetList);
    } else {
      addFinishedMedia(media, mediaOptional, user, targetList);
    }
    return Response.ok()
        .entity(mediaType + " added to " + (isFinished ? "finished" : "wanted") + " list.")
        .build();
  }

  private static <T extends Media> void addWantedMedia(
      T media, Optional<T> mediaOptional, User user, Set<T> usersWantedMedia) {
    if (mediaOptional.isPresent()) {
      T t = mediaOptional.get();
      Set<User> users = t.getUsersThatWantMedia();
      if (!users.contains(user)) {
        persistWantedMedia(t, usersWantedMedia, user);
      }
    } else {
      persistWantedMedia(media, usersWantedMedia, user);
    }
  }

  private static <T extends Media> void addFinishedMedia(
      T media, Optional<T> mediaOptional, User user, Set<T> usersWatchedMedia) {
    if (mediaOptional.isPresent()) {
      T t = mediaOptional.get();
      Set<User> users = t.getUsersThatFinishedMedia();
      if (!users.contains(user)) {
        persistFinishedMedia(t, usersWatchedMedia, user);
      }
    } else {
      persistFinishedMedia(media, usersWatchedMedia, user);
    }
  }

  private static <T extends Media> void persistWantedMedia(
      T media, Set<T> usersWantedMedia, User user) {
    if (!media.isPersistent()) {
      media.persist();
    }
    usersWantedMedia.add(media);
    media.addUserToWantedMedia(user);
  }

  private static <T extends Media> void persistFinishedMedia(
      T media, Set<T> usersWatchedMedia, User user) {
    if (!media.isPersistent()) {
      media.persist();
    }
    usersWatchedMedia.add(media);
    media.addUserToFinishedMedia(user);
  }
}
