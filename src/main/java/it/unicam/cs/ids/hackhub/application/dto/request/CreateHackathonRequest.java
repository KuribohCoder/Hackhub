package it.unicam.cs.ids.hackhub.application.dto.request;

import java.time.LocalDateTime;

/**
 * DTO per la richiesta di creazione di un nuovo hackathon.
 */
public record CreateHackathonRequest(
        String title,
        String description,
        String rules,
        String location,
        Double prizeAmount,
        LocalDateTime registrationDeadline,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer maxTeamMembers,
        Long organizerUserId,
        Long judgeUserId
) {}
