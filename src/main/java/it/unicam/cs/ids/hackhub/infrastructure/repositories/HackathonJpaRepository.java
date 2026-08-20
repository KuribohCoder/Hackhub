package it.unicam.cs.ids.hackhub.infrastructure.repositories;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonJpaRepository extends JpaRepository<Hackathon, Long> {
}
