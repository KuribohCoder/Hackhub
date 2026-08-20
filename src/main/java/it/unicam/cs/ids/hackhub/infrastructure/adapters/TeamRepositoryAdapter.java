package it.unicam.cs.ids.hackhub.infrastructure.adapters;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ITeamRepository;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import it.unicam.cs.ids.hackhub.infrastructure.repositories.TeamJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TeamRepositoryAdapter implements ITeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    public TeamRepositoryAdapter(TeamJpaRepository teamJpaRepository) {
        this.teamJpaRepository = teamJpaRepository;
    }

    @Override
    public Team save(Team team) {
        return teamJpaRepository.save(team);
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamJpaRepository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        if (!teamJpaRepository.existsById(id)) {
            return false;
        }
        teamJpaRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Team> findTeamByUserId(Long userId) {
        return teamJpaRepository.findByMembers_Id(userId);
    }
}
