package org.acme.services;

import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.FriendshipDTO;
import org.acme.dtos.UserDTO;
import org.acme.entities.Friendship;
import org.acme.entities.User;

import java.util.*;

@ApplicationScoped
public class FriendshipService {

    @Transactional
    public Response sendFriendRequest(String requesterUserName, String recipientUserName) {
        User requester = User.find("userName", requesterUserName).firstResult();
        User recipient = User.find("userName", recipientUserName).firstResult();

        // Check if there is an existing friendship request
        Optional<Friendship> existingFriendship = Friendship.find("#Friendship.findAnyPending", Parameters.with("userName", requesterUserName).and("friendUserName", recipientUserName))
                .firstResultOptional();
        if (existingFriendship.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("Friend request already sent.").build();
        }

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setRecipient(recipient);
        friendship.setStatus(Friendship.FriendshipStatus.PENDING);

        friendship.persist();
        return Response.ok().entity("Friend request sent.").build();
    }

    @Transactional
    public Response acceptFriendRequest(String recipientUserName, String requesterUserName) {
        Optional<Friendship> friendshipOptional = Friendship.find("#Friendship.findPending", Parameters.with("userName", requesterUserName).and("friendUserName", recipientUserName)).firstResultOptional();
        if (friendshipOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Friend request not found or already handled.").build();
        }
        Friendship friendship = friendshipOptional.get();
        friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
        friendship.persist();
        return Response.ok().entity("Friend request accepted.").build();
    }

    @Transactional
    public Response rejectFriendRequest(String recipientUserName, String requesterUserName) {
        Optional<Friendship> friendshipOptional = Friendship.find("#Friendship.findPending", Parameters.with("userName", requesterUserName).and("friendUserName", recipientUserName)).firstResultOptional();
        if (friendshipOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Friend request not found or already handled.").build();
        }
        Friendship friendship = friendshipOptional.get();
        friendship.setStatus(Friendship.FriendshipStatus.REJECTED);
        friendship.delete();
        return Response.ok().entity("Friend request rejected.").build();
    }

//    public Set<Friendship> getFriends(Long userId) {
//        User user = User.findById(userId);
//        return user.getFriendships();
//    }

    public List<UserDTO> getFriends(String userName) {
        List<Friendship> friendships = Friendship.find("#Friendship.findAccepted", Parameters.with("userName", userName)).list();
        List<UserDTO> friends = new ArrayList<>();
        for (Friendship f : friendships) {
            UserDTO userDTO;
            if (userName.equals(f.getRecipient().getUserName())) {
                userDTO = new UserDTO(f.getRequester());
            } else {
                userDTO = new UserDTO(f.getRecipient());
            }
            friends.add(userDTO);
        }
        return friends;
    }
}
