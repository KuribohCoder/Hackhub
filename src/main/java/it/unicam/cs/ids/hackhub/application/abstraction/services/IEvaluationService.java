package it.unicam.cs.ids.hackhub.application.abstraction.services;

import java.util.List;

import it.unicam.cs.ids.hackhub.domain.model.Evaluation;

/**
 * Servizio per la gestione e la consultazione delle valutazioni espresse dai giudici.
 */
public interface IEvaluationService {

    /**
     * Recupera tutte le valutazioni effettuate da uno specifico giudice.
     *
     * @param judgeId l'ID del giudice
     * @return lista delle valutazioni corrispondenti
     */
    List<Evaluation> getEvaluationsByJudge(Long judgeId);
}