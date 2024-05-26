package org.acme.dtos;

import io.quarkus.hibernate.orm.panache.common.NestedProjectedClass;
import org.acme.entities.Friendship.FriendshipStatus;

public class FriendshipDTO {
    public Long id;
    public RequesterDto requester;
    public RecipientDto recipient;
    public FriendshipStatus status;

    public FriendshipDTO(Long id, RequesterDto requester, RecipientDto recipient, FriendshipStatus status) {
        this.id = id;
        this.requester = requester;
        this.recipient = recipient;
        this.status = status;
    }

    @NestedProjectedClass
    public static class RequesterDto {
        public String userName;

        public RequesterDto(String userName) {
            this.userName = userName;
        }
    }

    @NestedProjectedClass
    public static class RecipientDto {
        public String userName;

        public RecipientDto(String userName) {
            this.userName = userName;
        }
    }
}
