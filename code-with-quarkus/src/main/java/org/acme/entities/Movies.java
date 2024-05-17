package org.acme.entities;

import jakarta.persistence.*;
import org.acme.enums.Genre;

@Entity
@Table(name = "movies")
public class Movies extends Media {
    @Column(nullable = false)
    String name;
    String movieUri;
    @Enumerated(EnumType.STRING)
    Genre genre;

}
