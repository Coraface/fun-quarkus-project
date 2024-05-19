package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import org.acme.enums.MediaGenre;

@MappedSuperclass
public class Media extends PanacheEntity {
    @Column(nullable = false, name = "title")
    protected String title;
    @Column(name = "image_uri")
    protected String imageUri;
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    protected MediaGenre genre;
    @Column(name = "rating")
    protected double rating;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MediaGenre getGenre() {
        return genre;
    }

    public void setGenre(MediaGenre genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
