package org.acme.dtos;

import lombok.AllArgsConstructor;
import org.acme.entities.Media.MediaGenre;

@AllArgsConstructor
public final class VideoGameDTO implements MediaDTO {
    public String title;
    public int year;
    public String imageUri;
    public MediaGenre genre;
    public double rating;
    public String developer;
}
