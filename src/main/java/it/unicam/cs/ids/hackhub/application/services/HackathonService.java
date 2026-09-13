package it.unicam.cs.ids.hackhub.application.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IHackathonRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ITeamRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IUserRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.services.IHackathonService;
import it.unicam.cs.ids.hackhub.application.dto.request.CreateHackathonRequest;
import it.unicam.cs.ids.hackhub.domain.builder.HackathonBuilder;
import it.unicam.cs.ids.hackhub.domain.enums.HackathonStatus;
import it.unicam.cs.ids.hackhub.domain.enums.Role;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import it.unicam.cs.ids.hackhub.domain.model.User;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class HackathonService implements IHackathonService {

    private final IHackathonRepository hackathonRepository;
    private final ITeamRepository teamRepository;
    private final IUserRepository userRepository;
    public HackathonService(IHackathonRepository hackathonRepository,
                            ITeamRepository teamRepository,
                            IUserRepository userRepository) {
        this.hackathonRepository = hackathonRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Hackathon createHackathon(CreateHackathonRequest request) {
        User organizer = userRepository.findById(request.organizerUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Organizzatore non trovato con id: " + request.organizerUserId()));

        if (organizer.getRole() != Role.ORGANIZER) {
            throw new IllegalStateException(
                    "L'utente con id " + request.organizerUserId() + " non ha il ruolo di ORGANIZER.");
        }

        User judge = null;
        if (request.judgeUserId() != null) {
            judge = userRepository.findById(request.judgeUserId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Giudice non trovato con id: " + request.judgeUserId()));
            if (judge.getRole() != Role.JUDGE) {
                throw new IllegalStateException(
                        "L'utente con id " + request.judgeUserId() + " non ha il ruolo di JUDGE.");
            }
        }

        Hackathon hackathon = new HackathonBuilder()
                .withTitle(request.title())
                .withDescription(request.description())
                .withRules(request.rules())
                .withLocation(request.location())
                .withPrizeAmount(request.prizeAmount())
                .withRegistrationDeadline(request.registrationDeadline())
                .withStartDate(request.startDate())
                .withEndDate(request.endDate())
                .withMaxTeamMembers(request.maxTeamMembers())
                .withOrganizer(organizer)
                .withJudge(judge)
                .build();

        return hackathonRepository.save(hackathon);
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
        Team team = teamRepository.findTeamByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "L'utente con id " + userId + " non appartiene a nessun team."));

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Hackathon non trovato con id: " + hackathonId));

        hackathon.unregisterTeam(team);
        hackathonRepository.save(hackathon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hackathon> getAllHackathons() {
        return hackathonRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Hackathon getHackathonById(Long id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon non trovato con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hackathon> getAllMyHackathons(Long userId) {
        return hackathonRepository.findByStaffMemberId(userId);
    }

    @Override
    public void addMentor(Long hackathonId, Long userId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Hackathon non trovato con id: " + hackathonId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utente non trovato con id: " + userId));

        if (user.getRole() != Role.MENTOR) {
            throw new IllegalStateException(
                    "L'utente con id " + userId + " non ha il ruolo di MENTOR.");
        }

        hackathon.addMentor(user);
        hackathonRepository.save(hackathon);
    }

    @Override
    public void cancelHackathon(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Hackathon non trovato con id: " + hackathonId));

        if (hackathon.getStatus() != HackathonStatus.REGISTRATION_OPEN) {
            throw new IllegalStateException(
                    "L'hackathon può essere annullato solo durante la fase di iscrizione (REGISTRATION_OPEN).");
        }

        hackathon.setStatus(HackathonStatus.CANCELLED);
        hackathonRepository.save(hackathon);
    }

    @Override
    public void deleteHackathon(Long hackathonId, Long requestingUserId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Hackathon non trovato con id: " + hackathonId));

        if (hackathon.getStatus() != HackathonStatus.CONCLUDED) {
            throw new IllegalStateException(
                    "L'hackathon può essere eliminato solo se si trova nello stato CONCLUDED.");
        }

        if (!hackathon.getOrganizerUser().getId().equals(requestingUserId)) {
            throw new IllegalStateException(
                    "Solo l'organizzatore può eliminare questo hackathon.");
        }

        hackathonRepository.delete(hackathon);
    }
}
