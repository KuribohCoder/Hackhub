package it.unicam.cs.ids.hackhub.infrastructure.repositories;

import it.unicam.cs.ids.hackhub.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByMembers_Id(Long userId);
}
