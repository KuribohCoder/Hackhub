package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.HackathonResponse;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import it.unicam.cs.ids.hackhub.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public final class HackathonMapper {

    private HackathonMapper() {}

    public static HackathonResponse toResponse(Hackathon hackathon) {
        if (hackathon == null) {
            return null;
        }

        List<Long> registeredTeamIds = new ArrayList<>();
        if (hackathon.getRegisteredTeams() != null) {
            for (Team team : hackathon.getRegisteredTeams()) {
                if (team != null && team.getId() != null) {
                    registeredTeamIds.add(team.getId());
                }
            }
        }

        List<Long> mentorUserIds = new ArrayList<>();
        if (hackathon.getMentors() != null) {
            for (User mentor : hackathon.getMentors()) {
                if (mentor != null && mentor.getId() != null) {
                    mentorUserIds.add(mentor.getId());
                }
            }
        }

        return new HackathonResponse(
                hackathon.getId(),
                hackathon.getTitle(),
                hackathon.getDescription(),
                hackathon.getRules(),
                hackathon.getLocation(),
                hackathon.getPrizeAmount(),
                hackathon.getRegistrationDeadline(),
                hackathon.getStartDate(),
                hackathon.getEndDate(),
                hackathon.getMaxTeamMembers(),
                hackathon.getStatus() != null ? hackathon.getStatus().name() : null,
                hackathon.getOrganizerUser() != null ? hackathon.getOrganizerUser().getId() : null,
                hackathon.getJudgeUser() != null ? hackathon.getJudgeUser().getId() : null,
                registeredTeamIds,
                mentorUserIds,
                hackathon.getWinningTeam() != null ? hackathon.getWinningTeam().getId() : null
        );
    }
}
