package it.unicam.cs.ids.hackhub.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record HackathonResponse(
        Long id,
        String title,
        String description,
        String rules,
        String location,
        Double prizeAmount,
        LocalDateTime registrationDeadline,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer maxTeamMembers,
        String status,
        Long organizerUserId,
        Long judgeUserId,
        List<Long> registeredTeamIds,
        List<Long> mentorUserIds,
        Long winningTeamId
) {}
