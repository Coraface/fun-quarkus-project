package org.acme.dtos;

import lombok.AllArgsConstructor;
import org.acme.entities.Friendship.FriendshipStatus;

@AllArgsConstructor
public class UsersFriendshipCustomDTO {
    public Long id;
    public String requester;
    public String recipient;
    public FriendshipStatus status;
}
