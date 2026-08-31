package it.unicam.cs.ids.hackhub.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String name,
        String surname,
        String email,
        String phone,
        LocalDate birthDate,
        String gender,
        String iban,
        String role,
        Long teamId,
        LocalDateTime createdAt
) {}
