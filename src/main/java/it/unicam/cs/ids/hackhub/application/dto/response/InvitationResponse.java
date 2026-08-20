package it.unicam.cs.ids.hackhub.application.dto.response;

import java.time.LocalDateTime;

public record InvitationResponse(
        Long id,
        Long senderUserId,
        String senderName,
        Long targetUserId,
        String targetName,
        String status,
        LocalDateTime invitedAt
) {
}
