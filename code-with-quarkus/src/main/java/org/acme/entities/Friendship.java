package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "friendship")
@NamedQueries({
        @NamedQuery(name = "Friendship.findAnyPending", query = "SELECT f FROM Friendship f WHERE (f.requester.userName = :userName AND f.recipient.userName = :friendUserName) OR (f.recipient.userName = :userName AND f.requester.userName = :friendUserName) AND f.status = 'PENDING'"),
        @NamedQuery(name = "Friendship.findPending", query = "SELECT f FROM Friendship f WHERE (f.requester.userName = :userName AND f.recipient.userName = :friendUserName) AND f.status = 'PENDING'"),
        @NamedQuery(name = "Friendship.findAccepted", query = "SELECT f FROM Friendship f WHERE (f.requester.userName = :userName OR f.recipient.userName = :userName) AND f.status = 'ACCEPTED'")
})
public class Friendship extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User recipient;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    public enum FriendshipStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}