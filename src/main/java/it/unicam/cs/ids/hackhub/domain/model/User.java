package it.unicam.cs.ids.hackhub.domain.model;

import it.unicam.cs.ids.hackhub.domain.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entità che rappresenta un utente registrato nella piattaforma Hackhub.
 * Contiene i dati anagrafici, credenziali, ruolo attivo nel sistema e eventuale team di appartenenza.
 */
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String surname;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", length = 255)
    private String password;

    @Column(length = 50)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 10)
    private String gender;

    @Column(length = 50)
    private String iban;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.GENERIC_USER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected User() {}

    public User(String name, String email) {
        this.name  = name;
        this.email = email;
        this.role  = Role.GENERIC_USER;
    }

    public User(String username, String name, String surname, String email, String password,
                String phone, LocalDate birthDate, String gender, String iban) {
        this.username  = username;
        this.name      = name;
        this.surname   = surname;
        this.email     = email;
        this.password  = password;
        this.phone     = phone;
        this.birthDate = birthDate;
        this.gender    = gender;
        this.iban      = iban;
        this.role      = Role.GENERIC_USER;
    }

    @PrePersist
    protected void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId()                  { return id; }
    public String getUsername()          { return username; }
    public String getName()              { return name; }
    public String getSurname()           { return surname; }
    public String getEmail()             { return email; }
    public String getPassword()          { return password; }
    public String getPhone()             { return phone; }
    public LocalDate getBirthDate()      { return birthDate; }
    public String getGender()            { return gender; }
    public String getIban()              { return iban; }
    public Role getRole()                { return role; }
    public Team getTeam()                { return team; }
    public LocalDateTime getCreatedAt()   { return createdAt; }

    public void setUsername(String username)   { this.username = username; }
    public void setName(String name)           { this.name = name; }
    public void setSurname(String surname)     { this.surname = surname; }
    public void setEmail(String email)         { this.email = email; }
    public void setPassword(String password)   { this.password = password; }
    public void setPhone(String phone)         { this.phone = phone; }
    public void setBirthDate(LocalDate date)   { this.birthDate = date; }
    public void setGender(String gender)       { this.gender = gender; }
    public void setIban(String iban)           { this.iban = iban; }
    public void setRole(Role role)             { this.role = role; }
    public void setTeam(Team team)             { this.team = team; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{id=%d, name='%s', email='%s', role=%s}".formatted(id, name, email, role);
    }
}
