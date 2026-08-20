package it.unicam.cs.ids.hackhub.application.services;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IHackathonRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ITeamRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.services.IHackathonService;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HackathonService implements IHackathonService {

    private final IHackathonRepository hackathonRepository;
    private final ITeamRepository teamRepository;

    public HackathonService(IHackathonRepository hackathonRepository,
                            ITeamRepository teamRepository) {
        this.hackathonRepository = hackathonRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void registerTeamToHackathon(Long userId, Long hackathonId) {
        Team team = teamRepository.findTeamByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "L'utente con id " + userId + " non appartiene a nessun team."));

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Hackathon non trovato con id: " + hackathonId));

        hackathon.registerTeam(team);
        hackathonRepository.save(hackathon);
    }

    @Override
    public void unregisterTeamFromHackathon(Long userId, Long hackathonId) {
    }
}
