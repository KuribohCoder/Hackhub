package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.InvitationResponse;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;

public final class InvitationMapper {
    private InvitationMapper() {
    }

    public static InvitationResponse toResponse(Invitation invitation) {
        return new InvitationResponse(
                invitation.getId(),
                invitation.getSenderUser().getId(),
                invitation.getSenderUser().getName(),
                invitation.getInvitedUser().getId(),
                invitation.getInvitedUser().getName(),
                invitation.getStatus().name(),
                invitation.getInvitedAt()
        );
    }
}
