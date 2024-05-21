package org.acme.services;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.entities.Friendship;
import org.acme.entities.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class FriendshipService {

    @Transactional
    public void sendFriendRequest(Long requesterId, Long recipientId) {
        User requester = User.findById(requesterId);
        User recipient = User.findById(recipientId);

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setRecipient(recipient);
        friendship.setStatus(Friendship.FriendshipStatus.PENDING);

        friendship.persist();
    }

    @Transactional
    public void acceptFriendRequest(Long recipientId, Long requesterId) {
        Friendship friendship = Friendship.find("#Friendship.findPending", Parameters.with("userId", requesterId).and("friendId", recipientId)).firstResult();
        if (friendship != null) {
            friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
            friendship.persist();
        }
    }

    @Transactional
    public void rejectFriendRequest(Long recipientId, Long requesterId) {
        Friendship friendship = Friendship.find("#Friendship.findPending", Parameters.with("userId", requesterId).and("friendId", recipientId)).firstResult();
        if (friendship != null) {
            friendship.setStatus(Friendship.FriendshipStatus.REJECTED);
            friendship.persist();
        }
    }

//    public Set<Friendship> getFriends(Long userId) {
//        User user = User.findById(userId);
//        return user.getFriendships();
//    }

    public Set<User> getFriends(Long userId) {
        List<Friendship> friendships = Friendship.find("#Friendship.findAccepted", Parameters.with("userId", userId)).list();
        Set<User> friends = new HashSet<>();

        for (Friendship friendship : friendships) {
            if (friendship.getRequester().id.equals(userId)) {
                friends.add(friendship.getRecipient());
            } else {
                friends.add(friendship.getRequester());
            }
        }
        return friends;
    }
}
