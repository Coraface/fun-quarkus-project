package org.acme;

import lombok.Getter;
import org.acme.entities.Media;

@Getter
public class MediaRequest {
    private String type;
    private String title;
    private String uri;
    private Media.MediaGenre genre;
    // additional fields based on media type (e.g., director for movies, seasons for series)

    // getters and setters
}