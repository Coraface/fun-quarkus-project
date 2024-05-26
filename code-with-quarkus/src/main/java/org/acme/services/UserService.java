package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.entities.Media;
import org.acme.entities.User;

@ApplicationScoped
public class UserService {

    @Transactional
    public void addWantedMedia(Long userId, Media media) {
        // Persist media if it is not already persistent
        if (!media.isPersistent()) {
            media.persist();
        }

        User user = User.findById(userId);
        if (user != null) {
            user.addWantedMedia(media);
        }
    }
}
