package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.application.dto.request.UpdateSubmissionRequest;
import it.unicam.cs.ids.hackhub.domain.model.Submission;

import java.util.List;

/**
 * Servizio per la gestione e l'aggiornamento delle sottomissioni dei progetti per un hackathon.
 */
public interface ISubmissionService {

    /**
     * Aggiorna una sottomissione esistente (repository URL e descrizione) durante la fase IN_PROGRESS dell'hackathon.
     *
     * @param submissionId l'ID della sottomissione da aggiornare
     * @param request i nuovi dati della sottomissione
     * @return l'entità Submission aggiornata
     */
    Submission updateSubmission(Long submissionId, UpdateSubmissionRequest request);

    /**
     * Recupera tutte le sottomissioni relative a uno specifico hackathon.
     *
     * @param hackathonId l'ID dell'hackathon
     * @return lista delle sottomissioni registrate
     */
    List<Submission> getSubmissionsByHackathon(Long hackathonId);
}