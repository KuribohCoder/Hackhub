package it.unicam.cs.ids.hackhub.application.dto.request;

public record LoginRequest(
        String email,
        String password
) {}
