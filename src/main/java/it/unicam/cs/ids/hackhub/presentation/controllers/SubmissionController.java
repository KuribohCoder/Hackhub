package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.ISubmissionService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.SubmissionMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateSubmissionRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.SubmissionResponse;
import it.unicam.cs.ids.hackhub.domain.model.Submission;

/**
 * Controller per l'aggiornamento e la consultazione delle sottomissioni dei progetti.
 */
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final ISubmissionService submissionService;

    public SubmissionController(ISubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    /**
     * Aggiorna una sottomissione (URL repository e descrizione) durante la fase attiva dell'hackathon.
     *
     * @param id ID della sottomissione
     * @param request DTO con i dati aggiornati
     * @return 200 OK con DTO della sottomissione aggiornata
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResponse> updateSubmission(
            @PathVariable Long id,
            @RequestBody UpdateSubmissionRequest request) {
        Submission submission = submissionService.updateSubmission(id, request);
        return ResponseEntity.ok(SubmissionMapper.toResponse(submission));
    }

    /**
     * Recupera tutte le sottomissioni associate a uno specifico hackathon.
     *
     * @param hackathonId ID dell'hackathon
     * @return 200 OK con lista di DTO delle sottomissioni
     */
    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByHackathon(
            @PathVariable Long hackathonId) {
        List<Submission> submissions = submissionService.getSubmissionsByHackathon(hackathonId);
        List<SubmissionResponse> responseList = new ArrayList<>(submissions.size());

        for (Submission submission : submissions) {
            responseList.add(SubmissionMapper.toResponse(submission));
        }

        return ResponseEntity.ok(responseList);
    }
}