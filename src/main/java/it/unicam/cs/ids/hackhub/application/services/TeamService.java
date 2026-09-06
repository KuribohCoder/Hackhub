package it.unicam.cs.ids.hackhub.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ITeamRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IUserRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.services.ITeamService;
import it.unicam.cs.ids.hackhub.domain.enums.Role;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import it.unicam.cs.ids.hackhub.domain.model.User;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class TeamService implements ITeamService {

    private final ITeamRepository teamRepository;
    private final IUserRepository userRepository;

    public TeamService(ITeamRepository teamRepository, IUserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Team createTeam(String teamName, Long userId) {
        if (teamName == null || teamName.isBlank()) {
            throw new IllegalArgumentException("Il nome del team non può essere vuoto.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utente non trovato con id: " + userId));

        if (user.getTeam() != null) {
            throw new IllegalStateException(
                    "L'utente con id " + userId + " è già membro di un team.");
        }

        Team team = new Team(teamName, user);
        team.addMember(user, Role.TEAM_CREATOR);

        return teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long teamId, Long requestingUserId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Team non trovato con id: " + teamId));

        if (!team.getCreatorUser().getId().equals(requestingUserId)) {
            throw new IllegalStateException("Solo il creatore del team può eliminarlo.");
        }

        teamRepository.deleteById(teamId);
    }

    @Override
    public void removeMember(Long memberUserId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Team non trovato con id: " + teamId));

        User user = userRepository.findById(memberUserId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "L'utente con id " + memberUserId + " non è stato trovato."));

        if (user.getTeam() == null || !user.getTeam().getId().equals(teamId)) {
            throw new IllegalArgumentException(
                    "L'utente con id " + memberUserId + " non è membro del team " + teamId + ".");
        }

        team.removeMember(user);
        teamRepository.save(team);
    }

    @Override
    public void leaveTeam(Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Team non trovato con id: " + teamId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utente non trovato con id: " + userId));

        if (user.getTeam() == null || !user.getTeam().getId().equals(teamId)) {
            throw new IllegalArgumentException(
                    "L'utente con id " + userId + " non è membro del team " + teamId + ".");
        }

        if (team.getCreatorUser().getId().equals(userId)) {
            throw new IllegalStateException(
                    "Il creatore del team non può abbandonarlo; deve eliminarlo.");
        }

        team.removeMember(user);
        user.setRole(Role.GENERIC_USER);

        teamRepository.save(team);
        userRepository.save(user);
    }
}
