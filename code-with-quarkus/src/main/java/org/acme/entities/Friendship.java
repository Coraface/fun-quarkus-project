package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "friendship")
@NamedQueries({
        @NamedQuery(name = "Friendship.findPending", query = "SELECT f FROM Friendship f WHERE f.requester.id = :userId AND f.recipient.id = :friendId AND f.status = 'PENDING'"),
        @NamedQuery(name = "Friendship.findAccepted", query = "SELECT f FROM Friendship f WHERE f.requester.id = :userId OR f.recipient.id = :userId AND f.status = 'ACCEPTED'")
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

    public Friendship() {
    }

    public Friendship(User requester, User recipient) {
        this.requester = requester;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(requester, that.requester) && Objects.equals(recipient, that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requester, recipient);
    }
}