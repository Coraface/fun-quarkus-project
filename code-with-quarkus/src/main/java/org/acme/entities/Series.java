package org.acme.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "series")
public class Series extends Media {
    @Column(nullable = false)
    private int seasons;
    @Column(nullable = false)
    private int episodes;
    @ManyToMany(mappedBy = "wantedSeriesList")
    private Set<User> usersThatWant = new HashSet<>();
    @ManyToMany(mappedBy = "watchedSeriesList")
    private Set<User> usersThatWatched = new HashSet<>();
}
