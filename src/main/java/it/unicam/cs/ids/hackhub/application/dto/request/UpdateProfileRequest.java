package it.unicam.cs.ids.hackhub.application.dto.request;

import java.time.LocalDate;

public record UpdateProfileRequest(
        String username,
        String name,
        String surname,
        String email,
        String phone,
        LocalDate birthDate,
        String gender,
        String iban
) {}
