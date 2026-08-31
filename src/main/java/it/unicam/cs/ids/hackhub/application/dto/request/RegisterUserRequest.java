package it.unicam.cs.ids.hackhub.application.dto.request;

import java.time.LocalDate;

public record RegisterUserRequest(
        String username,
        String name,
        String surname,
        String email,
        String password,
        String phone,
        LocalDate birthDate,
        String gender,
        String iban
) {}
