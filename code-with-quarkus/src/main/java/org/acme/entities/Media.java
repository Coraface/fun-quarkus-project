package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.DEDUCTION;

@Setter
@Getter
@MappedSuperclass
@JsonTypeInfo(use = DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(Movie.class),
  @JsonSubTypes.Type(Series.class),
  @JsonSubTypes.Type(VideoGame.class)
})
@NamedQueries({
  // Existing Media
  @NamedQuery(
      name = "Media.findExistingMovie",
      query = "SELECT m FROM Movie m WHERE m.title = :title AND m.year = :year"),
  @NamedQuery(
      name = "Media.findExistingSeries",
      query = "SELECT s FROM Series s WHERE s.title = :title AND s.year = :year"),
  @NamedQuery(
      name = "Media.findExistingVideoGame",
      query = "SELECT v FROM VideoGame v WHERE v.title = :title AND v.year = :year"),

  // Wanted Media
  @NamedQuery(
      name = "Media.findUsersWantedMovies",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director, u.userName FROM Movie m JOIN "
              + "m.usersThatWant u WHERE u.userName = :userName"),
  @NamedQuery(
      name = "Media.findUsersWantedSeries",
      query =
          "SELECT s.title, s.year, s.imageUri, s.genre, s.rating, s.episodes, s.seasons FROM Series s JOIN "
              + "s.usersThatWant u WHERE u.userName = :userName"),
  @NamedQuery(
      name = "Media.findUsersWantedVideoGames",
      query =
          "SELECT v.title, v.year, v.imageUri, v.genre, v.rating, v.developer FROM VideoGame v JOIN "
              + "v.usersThatWant u WHERE u.userName = :userName"),

  // Finished Media
  @NamedQuery(
      name = "Media.findUsersWatchedMovies",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director, u.userName FROM Movie m JOIN "
              + "m.usersThatWatched u WHERE u.userName = :userName"),
  @NamedQuery(
      name = "Media.findUsersWatchedSeries",
      query =
          "SELECT s.title, s.year, s.imageUri, s.genre, s.rating, s.episodes, s.seasons FROM Series s JOIN "
              + "s.usersThatWatched u WHERE u.userName = :userName"),
  @NamedQuery(
      name = "Media.findUsersPlayedVideoGames",
      query =
          "SELECT v.title, v.year, v.imageUri, v.genre, v.rating, v.developer FROM VideoGame v JOIN "
              + "v.usersThatPlayed u WHERE u.userName = :userName"),

  // Existing Users Media
  @NamedQuery(
      name = "Media.findExistingUsersWantedMovie",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director FROM Movie m JOIN "
              + "m.usersThatWant u WHERE m.title = :title AND m.year = :year AND u.userName = :userName"),
  @NamedQuery(
      name = "Media.findExistingUsersFinishedMovie",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director FROM Movie m JOIN "
              + "m.usersThatWatched u WHERE m.title = :title AND m.year = :year AND u.userName = :userName")
})
public abstract class Media extends PanacheEntity {
  @Column(nullable = false)
  protected String title;

  @Column(nullable = false)
  protected int year;

  @Column(name = "image_uri")
  protected String imageUri;

  @Enumerated(EnumType.STRING)
  protected MediaGenre genre;

  @Column(name = "rating")
  protected double rating;

  public enum MediaGenre {
    DRAMA,
    HORROR,
    CRIME,
    THRILLER,
    ACTION,
    ADVENTURE,
    SCI_FI,
    COMEDY,
    ROMANTIC,
    DOCUMENTARY,
    ANIMATION,
    FANTASY
  }

  public enum MediaType {
    MOVIE,
    SERIES,
    VIDEO_GAME
  }

  public abstract void addUserToWantedMedia(User user);

  public abstract void addUserToFinishedMedia(User user);

  public abstract Set<User> getUsersThatWantMedia();

  public abstract Set<User> getUsersThatFinishedMedia();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Media movie = (Media) o;
    return title.equals(movie.title) && year == movie.year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, year);
  }
}
