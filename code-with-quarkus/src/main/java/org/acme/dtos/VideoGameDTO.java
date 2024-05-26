package org.acme.dtos;

import org.acme.entities.Media.MediaGenre;

public final class VideoGameDTO {
    public String title;
    public String imageUri;
    public MediaGenre genre;
    public double rating;
    public String developer;

    public VideoGameDTO(String title, String imageUri, MediaGenre genre, double rating, String developer) {
        this.title = title;
        this.imageUri = imageUri;
        this.genre = genre;
        this.rating = rating;
        this.developer = developer;
    }
}
