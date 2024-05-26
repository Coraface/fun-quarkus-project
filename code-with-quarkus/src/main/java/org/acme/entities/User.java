package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findExistingWantedMovies", query = "SELECT u FROM User u LEFT JOIN FETCH u.wantedMoviesList WHERE u.userName = :userName")
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

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "watched_movies",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<Movie> watchedMoviesList = new HashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "wanted_movies",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<Movie> wantedMoviesList = new HashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "watched_series",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "series_id"))
  private Set<Series> watchedSeriesList = new HashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "wanted_series",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "series_id"))
  private Set<Series> wantedSeriesList = new HashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "played_games",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "game_id"))
  private Set<VideoGame> playedVideoGamesList = new HashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST)
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
}
