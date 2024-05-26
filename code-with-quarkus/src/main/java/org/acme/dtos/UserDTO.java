package org.acme.dtos;

import org.acme.entities.User;

public class UserDTO {
    public String userName;
    public String fullName;
    public String email;
    public String photoUri;
    public String bio;

    public UserDTO(String userName, String fullName, String email, String photoUri, String bio) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.photoUri = photoUri;
        this.bio = bio;
    }

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.photoUri = user.getPhotoUri();
        this.bio = user.getBio();
    }
}