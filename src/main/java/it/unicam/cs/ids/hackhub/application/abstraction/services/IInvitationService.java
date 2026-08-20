package it.unicam.cs.ids.hackhub.application.abstraction.services;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import java.util.List;
public interface IInvitationService {
    Invitation sendInvitation(Long senderUserId, Long targetUserId);
    void rejectInvitation(Long invitationId);
    List<Invitation> getUserInvitations(Long userId);
}
