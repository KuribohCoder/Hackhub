package it.unicam.cs.ids.hackhub.application.abstraction.services;

import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Report;

/**
 * Servizio per la gestione e la visualizzazione delle segnalazioni (report) relative a un hackathon.
 */
public interface IReportService {

    /**
     * Recupera tutte le segnalazioni pervenute relative a uno specifico hackathon.
     *
     * @param hackathonId l'ID dell'hackathon
     * @return lista delle segnalazioni corrispondenti
     */
    List<Report> getReportsByHackathon(Long hackathonId);
}