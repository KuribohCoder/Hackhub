package it.unicam.cs.ids.hackhub.domain.model;

import it.unicam.cs.ids.hackhub.domain.enums.InvitationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User senderUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invited_user_id", nullable = false)
    private User invitedUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "invited_at", nullable = false, updatable = false)
    private LocalDateTime invitedAt;

    protected Invitation() {}

    public Invitation(User senderUser, User invitedUser) {
        this.senderUser  = senderUser;
        this.invitedUser = invitedUser;
    }

    @PrePersist
    protected void onPrePersist() {
        this.invitedAt = LocalDateTime.now();
    }

    public Long getId()                     { return id; }
    public User getSenderUser()            { return senderUser; }
    public User getInvitedUser()           { return invitedUser; }
    public InvitationStatus getStatus()    { return status; }
    public LocalDateTime getInvitedAt()    { return invitedAt; }

    public void setStatus(InvitationStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Lo stato dell'invito non può essere null.");
        }
        this.status = status;
    }

    public boolean isPending() {
        return this.status == InvitationStatus.PENDING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invitation other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Invitation{id=%d, status=%s, invitedAt=%s}".formatted(id, status, invitedAt);
    }
}
