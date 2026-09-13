package it.unicam.cs.ids.hackhub.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.state.HackathonState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Entità aggregata radice che rappresenta un evento Hackathon.
 * Gestisce le informazioni dell'evento, le iscrizioni dei team attraverso lo State Pattern
 * e le associazioni con lo staff (organizzatore, giudice, mentori).
 */
@Entity
@Table(name = "hackathon")
public final class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String rules;

    @Column(length = 150)
    private String location;

    @Column(name = "prize_amount")
    private Double prizeAmount;

    @Column(name = "registration_deadline", nullable = false)
    private LocalDateTime registrationDeadline;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "max_team_members", nullable = false)
    private Integer maxTeamMembers;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private HackathonStatus status = HackathonStatus.REGISTRATION_OPEN;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_user_id", nullable = false)
    private User organizerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judge_user_id")
    private User judgeUser;

    @ManyToMany
    @JoinTable(
        name = "hackathon_registration",
        joinColumns        = @JoinColumn(name = "hackathon_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> registeredTeams = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "hackathon_mentor",
        joinColumns        = @JoinColumn(name = "hackathon_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> mentors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winning_team_id")
    private Team winningTeam;

    @Transient
    private HackathonState currentState;

    protected Hackathon() {}

    public Hackathon(String title, String description,
                     LocalDateTime registrationDeadline,
                     LocalDateTime startDate, LocalDateTime endDate,
                     Integer maxTeamMembers, User organizerUser) {
        this.title                = title;
        this.description          = description;
        this.registrationDeadline = registrationDeadline;
        this.startDate            = startDate;
        this.endDate              = endDate;
        this.maxTeamMembers       = maxTeamMembers;
        this.organizerUser        = organizerUser;
        this.status               = HackathonStatus.REGISTRATION_OPEN;
        restoreState();
    }

    @PostLoad
    protected void restoreState() {
        this.currentState = HackathonState.fromStatus(this.status);
    }

    /**
     * Esegue l'iscrizione di un team all'hackathon delegando al currentState (State Pattern).
     *
     * @param team il team da iscrivere
     */
    public void registerTeam(Team team) {
        currentState.registerTeam(this, team);
    }

    /**
     * Esegue la cancellazione dell'iscrizione di un team delegando al currentState (State Pattern).
     *
     * @param team il team da disiscrivere
     */
    public void unregisterTeam(Team team) {
        currentState.unregisterTeam(this, team);
    }

    public void addRegisteredTeam(Team team) {
        if (!registeredTeams.contains(team)) {
            registeredTeams.add(team);
        }
    }

    public void removeRegisteredTeam(Team team) {
        registeredTeams.remove(team);
    }

    public Long getId()                             { return id; }
    public String getTitle()                        { return title; }
    public String getDescription()                  { return description; }
    public String getRules()                        { return rules; }
    public String getLocation()                     { return location; }
    public Double getPrizeAmount()                  { return prizeAmount; }
    public LocalDateTime getRegistrationDeadline()  { return registrationDeadline; }
    public LocalDateTime getStartDate()             { return startDate; }
    public LocalDateTime getEndDate()               { return endDate; }
    public Integer getMaxTeamMembers()              { return maxTeamMembers; }
    public User getOrganizerUser()                  { return organizerUser; }
    public User getJudgeUser()                      { return judgeUser; }
    public HackathonStatus getStatus()              { return status; }
    public Team getWinningTeam()                    { return winningTeam; }
    public HackathonState getCurrentState()          { return currentState; }

    public List<Team> getRegisteredTeams() {
        return Collections.unmodifiableList(registeredTeams);
    }

    public List<User> getMentors() {
        return Collections.unmodifiableList(mentors);
    }

    public void setTitle(String title)              { this.title = title; }
    public void setDescription(String description)  { this.description = description; }
    public void setRules(String rules)              { this.rules = rules; }
    public void setLocation(String location)        { this.location = location; }
    public void setPrizeAmount(Double prizeAmount)  { this.prizeAmount = prizeAmount; }
    public void setJudgeUser(User judgeUser)        { this.judgeUser = judgeUser; }

    /**
     * Imposta lo stato dell'hackathon e aggiorna contestualmente l'istanza dello State Pattern.
     *
     * @param status il nuovo HackathonStatus
     */
    public void setStatus(HackathonStatus status) {
        this.status       = status;
        this.currentState = HackathonState.fromStatus(status);
    }

    public void setWinningTeam(Team winningTeam) {
        this.winningTeam = winningTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hackathon other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hackathon{id=%d, title='%s', status=%s}".formatted(id, title, status);
    }

    /**
     * Aggiunge un mentore allo staff dell'hackathon se non già presente.
     *
     * @param user l'utente mentore da assegnare
     */
    public void addMentor(User user) {
        if (user != null && !mentors.contains(user)) {
            this.mentors.add(user);
        }
    }

    public void setRegisteredTeams(List<Team> registeredTeams) {
        this.registeredTeams = registeredTeams;
    }

    public void setMentors(List<User> mentors) {
        this.mentors = mentors;
    }
}
