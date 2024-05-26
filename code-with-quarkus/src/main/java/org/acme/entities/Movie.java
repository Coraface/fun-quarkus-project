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
      name = "Movie.findExistingWantedMovies",
      query =
          "SELECT m.title, m.year, m.imageUri, m.genre, m.rating, m.durationMinutes, m.director FROM Movie m JOIN "
              + "m.usersThatWant u WHERE m.title = :title AND m.year = :year AND u.userName = :userName")
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Movie movie = (Movie) o;
    return title.equals(movie.title) && year == movie.year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, year);
  }
}
