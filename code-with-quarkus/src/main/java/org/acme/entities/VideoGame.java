package org.acme.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "video_games")
public class VideoGame extends Media {
    private String developer;
    @ManyToMany(mappedBy = "wantedVideoGamesList")
    private Set<User> usersThatWant = new HashSet<>();
    @ManyToMany(mappedBy = "playedVideoGamesList")
    private Set<User> usersThatPlayed = new HashSet<>();

    @Override
    public void addUserToWantedMedia(User user) {
        usersThatWant.add(user);
    }

    @Override
    public void addUserToFinishedMedia(User user) {
        usersThatPlayed.add(user);
    }

    @Override
    public Set<User> getUsersThatWantMedia() {
        return getUsersThatWant();
    }

    @Override
    public Set<User> getUsersThatFinishedMedia() {
        return getUsersThatPlayed();
    }
}
