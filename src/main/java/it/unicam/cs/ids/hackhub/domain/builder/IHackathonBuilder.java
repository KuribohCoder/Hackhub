package it.unicam.cs.ids.hackhub.domain.builder;

import java.time.LocalDateTime;
import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.User;

/**
 * Interfaccia Builder per la costruzione incrementale e fluente di istanze di {@link Hackathon}.
 */
public interface IHackathonBuilder {

    IHackathonBuilder withTitle(String title);

    IHackathonBuilder withDescription(String description);

    IHackathonBuilder withRules(String rules);

    IHackathonBuilder withLocation(String location);

    IHackathonBuilder withPrizeAmount(Double prizeAmount);

    IHackathonBuilder withRegistrationDeadline(LocalDateTime registrationDeadline);

    IHackathonBuilder withStartDate(LocalDateTime startDate);

    IHackathonBuilder withEndDate(LocalDateTime endDate);

    IHackathonBuilder withMaxTeamMembers(Integer maxTeamMembers);

    IHackathonBuilder withOrganizer(User organizerUser);

    IHackathonBuilder withJudge(User judgeUser);

    IHackathonBuilder withMentors(List<User> mentors);

    IHackathonBuilder addMentor(User mentor);

    Hackathon build();
}
