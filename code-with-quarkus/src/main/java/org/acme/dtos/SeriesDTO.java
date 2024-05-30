package org.acme.dtos;

import lombok.AllArgsConstructor;
import org.acme.entities.Media.MediaGenre;

@AllArgsConstructor
public final class SeriesDTO implements MediaDTO {
    public String title;
    public int year;
    public String imageUri;
    public MediaGenre genre;
    public double rating;
    public int episodes;
    public int seasons;
}
