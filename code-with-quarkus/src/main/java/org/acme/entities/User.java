package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false, unique = true)
    String userName;
    String fullName;
    @Column(nullable = false, unique = true)
    String email;
    String profilePhoto;
    @Column(length = 500)
    String bio;

    @ManyToMany
    @JoinTable(
            name = "user_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    List<Movies> movies;
    @ManyToMany
    @JoinTable(
            name = "user_series",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "series_id")
    )
    List<Series> series;
    @ManyToMany
    @JoinTable(
            name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    List<Games> games;
}
