package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import java.util.List;
import java.util.Optional;

import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

public interface IHackathonRepository {

    Optional<Hackathon> findById(Long id);

    List<Hackathon> findAll();

    List<Hackathon> findByStaffMemberId(Long userId);

    Hackathon save(Hackathon hackathon);

    void delete(Hackathon hackathon);
}
