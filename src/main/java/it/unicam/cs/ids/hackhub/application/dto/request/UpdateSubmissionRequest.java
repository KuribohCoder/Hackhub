package it.unicam.cs.ids.hackhub.application.dto.request;

public record UpdateSubmissionRequest(
        String title,
        String description,
        String repositoryUrl
) {}