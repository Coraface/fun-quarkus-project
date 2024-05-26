package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

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
}
