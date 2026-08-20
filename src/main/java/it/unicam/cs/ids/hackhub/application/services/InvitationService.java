package it.unicam.cs.ids.hackhub.application.services;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IInvitationRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.ITeamRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IUserRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.services.IInvitationService;
import it.unicam.cs.ids.hackhub.domain.enums.InvitationStatus;
import it.unicam.cs.ids.hackhub.domain.enums.Role;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import it.unicam.cs.ids.hackhub.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvitationService implements IInvitationService {

    private final IInvitationRepository invitationRepository;
    private final IUserRepository       userRepository;
    private final ITeamRepository       teamRepository;

    public InvitationService(IInvitationRepository invitationRepository,
                             IUserRepository userRepository,
                             ITeamRepository teamRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository       = userRepository;
        this.teamRepository       = teamRepository;
    }

    @Override
    public Invitation sendInvitation(Long senderUserId, Long targetUserId) {
        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Mittente non trovato con id: " + senderUserId));

        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Destinatario non trovato con id: " + targetUserId));

        if (sender.getTeam() == null) {
            throw new IllegalStateException(
                    "Il mittente con id " + senderUserId + " non appartiene a nessun team.");
        }

        if (sender.getRole() != Role.TEAM_CREATOR) {
            throw new IllegalStateException("Solo il creatore del team può inviare inviti.");
        }

        if (target.getTeam() != null) {
            throw new IllegalStateException(
                    "L'utente invitato con id " + targetUserId + " è già membro di un team.");
        }

        return invitationRepository.save(new Invitation(sender, target));
    }

    @Override
    public void rejectInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Invito non trovato con id: " + invitationId));

        if (!invitation.isPending()) {
            throw new IllegalStateException(
                    "Impossibile rifiutare un invito che non è in stato PENDING.");
        }

        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invitation> getUserInvitations(Long userId) {
        return invitationRepository.findPendingByUserId(userId);
    }
}
