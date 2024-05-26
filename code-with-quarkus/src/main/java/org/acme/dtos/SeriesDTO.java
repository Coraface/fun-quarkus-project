package org.acme.dtos;

import org.acme.entities.Media.MediaGenre;

public final class SeriesDTO {
    public String title;
    public String imageUri;
    public MediaGenre genre;
    public double rating;
    public int episodes;
    public int seasons;

    public SeriesDTO(String title, String imageUri, MediaGenre genre, double rating, int episodes, int seasons) {
        this.title = title;
        this.imageUri = imageUri;
        this.genre = genre;
        this.rating = rating;
        this.episodes = episodes;
        this.seasons = seasons;
    }
}
