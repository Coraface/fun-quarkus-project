package org.acme.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "movies")
@NamedQueries({
  @NamedQuery(
      name = "Movie.findWantedMovies",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director FROM Movie m JOIN "
              + "m.usersThatWant u WHERE u.userName = :userName"),
  @NamedQuery(
      name = "Movie.findWatchedMovies",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director FROM Movie m JOIN "
              + "m.usersThatWatched u WHERE u.userName = :userName"),
  @NamedQuery(
      name = "Movie.findExistingWantedMovies",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director FROM Movie m JOIN "
              + "m.usersThatWant u WHERE m.title = :title AND m.year = :year AND u.userName = :userName"),
  @NamedQuery(
      name = "Movie.findExistingWantedMovie",
      query =
          "SELECT m FROM Movie m JOIN m.usersThatWant u WHERE m.title = :title AND m.year = :year")
})
public class Movie extends Media {
  @Column(nullable = false, name = "duration_minutes")
  private int durationMinutes;

  @Column(name = "director")
  private String director;

  @ManyToMany(mappedBy = "wantedMoviesList")
  private Set<User> usersThatWant = new HashSet<>();

  @ManyToMany(mappedBy = "watchedMoviesList")
  private Set<User> usersThatWatched = new HashSet<>();

  @Override
  public void addUserToWantedMedia(User user) {
    usersThatWant.add(user);
  }

  @Override
  public void addUserToFinishedMedia(User user) {
    usersThatWatched.add(user);
  }

  @Override
  public Set<User> getUsersThatWantMedia() {
    return getUsersThatWant();
  }

  @Override
  public Set<User> getUsersThatFinishedMedia() {
    return getUsersThatWatched();
  }
}
