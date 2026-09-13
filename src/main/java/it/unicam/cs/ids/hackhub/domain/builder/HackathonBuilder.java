package it.unicam.cs.ids.hackhub.domain.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.User;

/**
 * Implementazione del Builder Pattern per la creazione fluente e validata di {@link Hackathon}.
 */
public class HackathonBuilder implements IHackathonBuilder {

    private String title;
    private String description;
    private String rules;
    private String location;
    private Double prizeAmount;
    private LocalDateTime registrationDeadline;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxTeamMembers;
    private User organizerUser;
    private User judgeUser;
    private List<User> mentors = new ArrayList<>();

    public HackathonBuilder() {}

    @Override
    public HackathonBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public HackathonBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public HackathonBuilder withRules(String rules) {
        this.rules = rules;
        return this;
    }

    @Override
    public HackathonBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    @Override
    public HackathonBuilder withPrizeAmount(Double prizeAmount) {
        this.prizeAmount = prizeAmount;
        return this;
    }

    @Override
    public HackathonBuilder withRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
        return this;
    }

    @Override
    public HackathonBuilder withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public HackathonBuilder withEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public HackathonBuilder withMaxTeamMembers(Integer maxTeamMembers) {
        this.maxTeamMembers = maxTeamMembers;
        return this;
    }

    @Override
    public HackathonBuilder withOrganizer(User organizerUser) {
        this.organizerUser = organizerUser;
        return this;
    }

    @Override
    public HackathonBuilder withJudge(User judgeUser) {
        this.judgeUser = judgeUser;
        return this;
    }

    @Override
    public HackathonBuilder withMentors(List<User> mentors) {
        if (mentors != null) {
            this.mentors = new ArrayList<>(mentors);
        }
        return this;
    }

    @Override
    public HackathonBuilder addMentor(User mentor) {
        if (mentor != null && !this.mentors.contains(mentor)) {
            this.mentors.add(mentor);
        }
        return this;
    }

    @Override
    public Hackathon build() {
        if (title == null || title.isBlank()) {
            throw new IllegalStateException("Il titolo dell'hackathon è obbligatorio.");
        }
        if (registrationDeadline == null) {
            throw new IllegalStateException("La data di scadenza iscrizioni è obbligatoria.");
        }
        if (startDate == null) {
            throw new IllegalStateException("La data di inizio dell'hackathon è obbligatoria.");
        }
        if (endDate == null) {
            throw new IllegalStateException("La data di fine dell'hackathon è obbligatoria.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalStateException("La data di inizio non può essere successiva alla data di fine.");
        }
        if (registrationDeadline.isAfter(startDate)) {
            throw new IllegalStateException("La scadenza iscrizioni non può essere successiva all'inizio dell'hackathon.");
        }
        if (maxTeamMembers == null || maxTeamMembers < 1) {
            throw new IllegalStateException("Il numero massimo di membri per team deve essere almeno 1.");
        }
        if (organizerUser == null) {
            throw new IllegalStateException("L'organizzatore dell'hackathon è obbligatorio.");
        }

        Hackathon hackathon = new Hackathon(
                title,
                description,
                registrationDeadline,
                startDate,
                endDate,
                maxTeamMembers,
                organizerUser
        );
        hackathon.setRules(rules);
        hackathon.setLocation(location);
        hackathon.setPrizeAmount(prizeAmount);
        hackathon.setJudgeUser(judgeUser);
        if (mentors != null && !mentors.isEmpty()) {
            hackathon.setMentors(new ArrayList<>(mentors));
        }
        return hackathon;
    }
}
