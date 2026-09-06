package it.unicam.cs.ids.hackhub.application.dto.response;

public record EvaluationResponse(
        Long id,
        Long judgeId,
        Long submissionId,
        double score,
        String feedback
) {}