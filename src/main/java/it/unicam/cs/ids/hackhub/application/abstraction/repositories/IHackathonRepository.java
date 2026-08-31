package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

import java.util.List;
import java.util.Optional;

public interface IHackathonRepository {

    Optional<Hackathon> findById(Long id);

    List<Hackathon> findAll();

    List<Hackathon> findByStaffMemberId(Long userId);

    Hackathon save(Hackathon hackathon);
}
