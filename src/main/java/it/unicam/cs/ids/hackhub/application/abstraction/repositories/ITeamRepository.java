package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import it.unicam.cs.ids.hackhub.domain.model.Team;

import java.util.Optional;

public interface ITeamRepository {

    Team save(Team team);

    Optional<Team> findById(Long id);

    boolean deleteById(Long id);

    Optional<Team> findTeamByUserId(Long userId);
}
