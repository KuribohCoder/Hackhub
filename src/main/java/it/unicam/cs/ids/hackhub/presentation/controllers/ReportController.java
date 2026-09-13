package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IReportService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.ReportMapper;
import it.unicam.cs.ids.hackhub.application.dto.response.ReportResponse;
import it.unicam.cs.ids.hackhub.domain.model.Report;

/**
 * Controller per la consultazione delle segnalazioni/report inviati durante gli hackathon.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Recupera tutte le segnalazioni pervenute per uno specifico hackathon.
     *
     * @param hackathonId ID dell'hackathon
     * @return 200 OK con lista di DTO delle segnalazioni
     */
    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<ReportResponse>> getReportsByHackathon(
            @PathVariable Long hackathonId) {
        List<Report> reports = reportService.getReportsByHackathon(hackathonId);
        List<ReportResponse> responseList = new ArrayList<>(reports.size());

        for (Report report : reports) {
            responseList.add(ReportMapper.toResponse(report));
        }

        return ResponseEntity.ok(responseList);
    }
}