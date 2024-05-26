package org.acme.dtos;

import org.acme.entities.Media;

public class MovieDTO {
    public String title;
    public int year;
    public String imageUri;
    public Media.MediaGenre genre;
    public double rating;
    public int durationMinutes;
    public String director;

    public MovieDTO(String title, int year, String imageUri, Media.MediaGenre genre, double rating, int durationMinutes, String director) {
        this.title = title;
        this.year = year;
        this.imageUri = imageUri;
        this.genre = genre;
        this.rating = rating;
        this.durationMinutes = durationMinutes;
        this.director = director;
    }
}
