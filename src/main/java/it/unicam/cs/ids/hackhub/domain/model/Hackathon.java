package it.unicam.cs.ids.hackhub.domain.model;

import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.state.HackathonState;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hackathon")
public class Hackathon {

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

    public void registerTeam(Team team) {
        currentState.registerTeam(this, team);
    }

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
    public HackathonStatus getStatus()              { return status; }
    public User getOrganizerUser()                  { return organizerUser; }
    public User getJudgeUser()                      { return judgeUser; }
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
}
