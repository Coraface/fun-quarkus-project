package org.acme.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.FriendshipDTO;
import org.acme.dtos.UserDTO;
import org.acme.entities.Friendship;
import org.acme.entities.User;
import org.acme.services.FriendshipService;

import java.util.List;
import java.util.Set;

@Path("/users/{username}/friends")
@ApplicationScoped
public class FriendshipResource {

  @Inject FriendshipService friendshipService;

  @POST
  @Path("/{friend_username}")
  public Response sendFriendRequest(
      @PathParam("username") String userName, @PathParam("friend_username") String friendUserName) {
    return friendshipService.sendFriendRequest(userName, friendUserName);
  }

  @PUT
  @Path("/{friend_username}/accept")
  public Response acceptFriendRequest(
      @PathParam("username") String recipientUserName,
      @PathParam("friend_username") String requesterUserName) {
    return friendshipService.acceptFriendRequest(recipientUserName, requesterUserName);
  }

  @PUT
  @Path("/{friend_username}/reject")
  public Response rejectFriendRequest(
      @PathParam("username") String recipientUserName,
      @PathParam("friend_username") String requesterUserName) {
    return friendshipService.rejectFriendRequest(recipientUserName, requesterUserName);
  }

  @GET
  public List<UserDTO> getFriends(@PathParam("username") String userName) {
    return friendshipService.getFriends(userName);
  }

  @GET
  @Path("/friendships")
  public List<FriendshipDTO> getFriendships() {
    return Friendship.findAll().project(FriendshipDTO.class).list();
  }
}
