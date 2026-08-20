package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.TeamResponse;
import it.unicam.cs.ids.hackhub.domain.model.Team;

public final class TeamMapper {

    private TeamMapper() {
    }

    public static TeamResponse toResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getCreatorUser().getId(),
                team.getCreatedAt()
        );
    }
}
