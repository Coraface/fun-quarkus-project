package org.acme.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.UserDTO;
import org.acme.entities.Media;
import org.acme.entities.User;
import org.acme.services.UserService;

import java.util.List;

@Path("/users")
@ApplicationScoped
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Transactional
    public Response createUser(User user) {
        User.persist(user);
        return Response.ok().build();
    }

    @GET
    public List<UserDTO> getAllUsers() {
        return User.findAll().project(UserDTO.class).list();
    }

    @GET
    @Path("/{userId}")
    public UserDTO getUser(@PathParam("userId") Long userId) {
        return User.find("id", userId).project(UserDTO.class).firstResult();
    }

    @POST
    @Path("/{userId}/wanted")
    @Transactional
    public Response addWantedMedia(@PathParam("userId") Long userId, Media media) {
        userService.addWantedMedia(userId, media);
        return Response.ok().build();
    }
}