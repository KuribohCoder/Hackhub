package it.unicam.cs.ids.hackhub.application.abstraction.services;
import it.unicam.cs.ids.hackhub.domain.model.Team;
public interface ITeamService {
    Team createTeam(String teamName, Long userId);
    void deleteTeam(Long teamId, Long requestingUserId);
    void removeMember(Long memberUserId, Long teamId);
}
