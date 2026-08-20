package it.unicam.cs.ids.hackhub.application.dto.response;

import java.time.LocalDateTime;

public record TeamResponse(
        Long id,
        String name,
        Long creatorUserId,
        LocalDateTime createdAt
) {
}
