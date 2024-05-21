package org.acme.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entities.User;

import java.util.List;

@Path("/users")
@ApplicationScoped
public class UserResource {

    @POST
    @Transactional
    public Response createUser(User user) {
        User.persist(user);
        return Response.ok().build();
    }

    @GET
    public List<User> getAllUsers() {
        return User.findAll().list();
    }

    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId") Long userId) {
        return User.findById(userId);
    }
}