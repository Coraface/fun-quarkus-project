package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "users")
@NamedQueries({
  @NamedQuery(
      name = "User.findExistingMovies",
      // We use JOIN FETCH so that Hibernate initializes the mapped association,
      // in this case wantedMoviesList
      query =
          "SELECT u FROM User u LEFT JOIN FETCH u.wantedMoviesList LEFT JOIN FETCH u.watchedMoviesList WHERE u.userName = :userName"),
})
public class User extends PanacheEntity {

  @Column(nullable = false, unique = true)
  private String userName;

  private String fullName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "photo_uri")
  private String photoUri;

  @Column(length = 500)
  private String bio;

  @ManyToMany
  @JoinTable(
      name = "watched_movies",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<Movie> watchedMoviesList = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "wanted_movies",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<Movie> wantedMoviesList = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "watched_series",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "series_id"))
  private Set<Series> watchedSeriesList = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "wanted_series",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "series_id"))
  private Set<Series> wantedSeriesList = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "played_games",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "game_id"))
  private Set<VideoGame> playedVideoGamesList = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "wanted_games",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "game_id"))
  private Set<VideoGame> wantedVideoGamesList = new HashSet<>();

  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Friendship> friendships = new HashSet<>();

  public void addWantedMedia(Media media) {
    if (media instanceof Movie) {
      wantedMoviesList.add((Movie) media);
    } else if (media instanceof Series) {
      wantedSeriesList.add((Series) media);
    } else {
      wantedVideoGamesList.add((VideoGame) media);
    }
  }

  public void addWatchedMedia(Media media) {
    if (media instanceof Movie) {
      watchedMoviesList.add((Movie) media);
    } else if (media instanceof Series) {
      watchedSeriesList.add((Series) media);
    } else {
      playedVideoGamesList.add((VideoGame) media);
    }
  }

  public <T extends Media> Set<T> getWantedMediaList(T media) {
    if (media instanceof Movie) {
      return (Set<T>) getWantedMoviesList();
    } else if (media instanceof Series) {
      return (Set<T>) getWantedSeriesList();
    } else if (media instanceof VideoGame) {
      return (Set<T>) getWantedVideoGamesList();
    } else {
      return Set.of();
    }
  }

  public <T extends Media> Set<T> getFinishedMediaList(T media) {
    if (media instanceof Movie) {
      return (Set<T>) getWatchedMoviesList();
    } else if (media instanceof Series) {
      return (Set<T>) getWatchedSeriesList();
    } else if (media instanceof VideoGame) {
      return (Set<T>) getPlayedVideoGamesList();
    } else {
      return Set.of();
    }
  }

  public <T extends Media> void addUserToWantedMedia(User user, T media) {
    if (media instanceof Movie) {
      ((Movie) media).getUsersThatWant().add(user);
    } else if (media instanceof Series) {
      ((Series) media).getUsersThatWant().add(user);
    } else if (media instanceof VideoGame) {
      ((VideoGame) media).getUsersThatWant().add(user);
    }
  }

  public <T extends Media> void addUserToWatchedMedia(User user, T media) {
    if (media instanceof Movie) {
      ((Movie) media).getUsersThatWatched().add(user);
    } else if (media instanceof Series) {
      ((Series) media).getUsersThatWatched().add(user);
    } else if (media instanceof VideoGame) {
      ((VideoGame) media).getUsersThatPlayed().add(user);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return userName.equals(user.userName) && email.equals(user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, email);
  }
}
