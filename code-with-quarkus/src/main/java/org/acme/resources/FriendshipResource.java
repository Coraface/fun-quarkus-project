package org.acme.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.entities.Friendship;
import org.acme.entities.User;
import org.acme.services.FriendshipService;

import java.util.List;
import java.util.Set;

@Path("/users/{userId}/friends")
@ApplicationScoped
public class FriendshipResource {

    @Inject
    FriendshipService friendshipService;

    @POST
    @Path("/{friendId}")
    @Transactional
    public Response sendFriendRequest(@PathParam("userId") Long userId, @PathParam("friendId") Long friendId) {
        friendshipService.sendFriendRequest(userId, friendId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{friendId}/accept")
    @Transactional
    public Response acceptFriendRequest(@PathParam("userId") Long recipientId, @PathParam("friendId") Long requesterId) {
        friendshipService.acceptFriendRequest(recipientId, requesterId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{friendId}/reject")
    @Transactional
    public Response rejectFriendRequest(@PathParam("userId") Long recipientId, @PathParam("friendId") Long requesterId) {
        friendshipService.rejectFriendRequest(recipientId, requesterId);
        return Response.ok().build();
    }

    @GET
    public Set<User> getFriends(@PathParam("userId") Long userId) {
        return friendshipService.getFriends(userId);
    }

    @GET
    @Path("/friendships")
    public List<Friendship> getFriendships() {
        return Friendship.findAll().list();
    }
}