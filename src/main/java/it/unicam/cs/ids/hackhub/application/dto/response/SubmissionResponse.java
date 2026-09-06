package it.unicam.cs.ids.hackhub.application.dto.response;

public record SubmissionResponse(
        Long id,
        String title,
        String description,
        String repositoryUrl
) {}