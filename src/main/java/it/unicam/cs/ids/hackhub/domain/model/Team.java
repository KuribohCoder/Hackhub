package it.unicam.cs.ids.hackhub.domain.model;

import it.unicam.cs.ids.hackhub.domain.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creatorUser;

    @OneToMany(mappedBy = "team")
    private List<User> members = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Team() {}

    public Team(String name, User creatorUser) {
        this.name        = name;
        this.creatorUser = creatorUser;
    }

    @PrePersist
    protected void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public User getCreatorUser()        { return creatorUser; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    public List<User> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void setName(String name) { this.name = name; }

    public void addMember(User user, Role roleInTeam) {
        if (user == null) {
            throw new IllegalArgumentException("L'utente non può essere null.");
        }
        if (!members.contains(user)) {
            members.add(user);
            user.setTeam(this);
            user.setRole(roleInTeam);
        }
    }

    public void removeMember(User user) {
        if (user != null && members.remove(user)) {
            user.setTeam(null);
            user.setRole(Role.GENERIC_USER);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Team{id=%d, name='%s', createdAt=%s}".formatted(id, name, createdAt);
    }
}
