package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.ReportResponse;
import it.unicam.cs.ids.hackhub.domain.model.Report;

public class ReportMapper {

    public static ReportResponse toResponse(Report report) {
        if (report == null) {
            return null;
        }
        return new ReportResponse(
                report.getId(),
                report.getHackathonId(),
                report.getContent()
        );
    }
}