package org.acme.dtos;

import java.util.Set;

public class MediaDTO {
    public Set<UserDTO> usersThatWant;
    public Set<UserDTO> usersThatWatched;

    public MediaDTO(Set<UserDTO> usersThatWant, Set<UserDTO> usersThatWatched) {
        this.usersThatWant = usersThatWant;
        this.usersThatWatched = usersThatWatched;
    }
}
