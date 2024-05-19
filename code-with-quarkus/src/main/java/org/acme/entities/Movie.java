package org.acme.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "movies")
public abstract class Movie extends Media {
    @Column(nullable = false, name = "durationMins")
    private int durationMinutes;
    @Column(name = "director")
    private String director;
    @ManyToMany(mappedBy = "moviesList")
    private Set<User> users;

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
