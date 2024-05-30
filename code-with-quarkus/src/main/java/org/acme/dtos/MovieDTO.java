package org.acme.dtos;

import lombok.AllArgsConstructor;
import org.acme.entities.Media;

import java.util.Set;

public class MovieDTO implements MediaDTO {
    public String title;
    public int year;
    public String imageUri;
    public Media.MediaGenre genre;
    public double rating;
    public int durationMinutes;
    public String director;
    // TODO: remove, not allowed
    public Set<String> usersThatWant;

    public MovieDTO(String title, int year, String imageUri, Media.MediaGenre genre, double rating, int durationMinutes, String director, Set<String> usersThatWant) {
        this.title = title;
        this.year = year;
        this.imageUri = imageUri;
        this.genre = genre;
        this.rating = rating;
        this.durationMinutes = durationMinutes;
        this.director = director;
        this.usersThatWant = usersThatWant;
    }
}
