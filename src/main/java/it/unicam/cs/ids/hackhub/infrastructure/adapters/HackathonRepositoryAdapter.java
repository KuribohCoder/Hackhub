package it.unicam.cs.ids.hackhub.infrastructure.adapters;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IHackathonRepository;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.infrastructure.repositories.HackathonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HackathonRepositoryAdapter implements IHackathonRepository {
    private final HackathonJpaRepository jpaRepository;

    public HackathonRepositoryAdapter(HackathonJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Hackathon> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Hackathon save(Hackathon hackathon) {
        return jpaRepository.save(hackathon);
    }
}
