package it.unicam.cs.ids.hackhub.application.dto.response;

public record ReportResponse(
        Long id,
        Long hackathonId,
        String content
) {}