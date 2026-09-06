package it.unicam.cs.ids.hackhub.infrastructure.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IHackathonRepository;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.infrastructure.repositories.HackathonJpaRepository;

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
    public List<Hackathon> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Hackathon> findByStaffMemberId(Long userId) {
        return jpaRepository.findByStaffMemberId(userId);
    }

    @Override
    public Hackathon save(Hackathon hackathon) {
        return jpaRepository.save(hackathon);
    }

    @Override
    public void delete(Hackathon hackathon) {
        jpaRepository.delete(hackathon);
    }
}
