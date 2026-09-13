package it.unicam.cs.ids.hackhub.application.abstraction.services;

import java.util.List;

import it.unicam.cs.ids.hackhub.application.dto.request.CreateHackathonRequest;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

/**
 * Servizio per la gestione del ciclo di vita degli hackathon,
 * delle iscrizioni dei team e dell'assegnazione dello staff.
 */
public interface IHackathonService {

    /**
     * Crea un nuovo hackathon validando i parametri e utilizzando il Builder Pattern.
     *
     * @param request i dati di creazione dell'hackathon
     * @return l'entità Hackathon creata e salvata
     */
    Hackathon createHackathon(CreateHackathonRequest request);

    /**
     * Iscrive il team di appartenenza dell'utente all'hackathon specificato.
     *
     * @param userId l'ID dell'utente membro/leader del team
     * @param hackathonId l'ID dell'hackathon a cui iscrivere il team
     */
    void registerTeamToHackathon(Long userId, Long hackathonId);

    /**
     * Annulla l'iscrizione del team dell'utente dall'hackathon.
     *
     * @param userId l'ID dell'utente membro del team
     * @param hackathonId l'ID dell'hackathon da cui ritirare il team
     */
    void unregisterTeamFromHackathon(Long userId, Long hackathonId);

    /**
     * Restituisce la lista di tutti gli hackathon presenti nel sistema.
     *
     * @return lista completa degli hackathon
     */
    List<Hackathon> getAllHackathons();

    /**
     * Recupera un hackathon dato il suo identificativo univoco.
     *
     * @param id l'ID dell'hackathon
     * @return l'entità Hackathon corrispondente
     */
    Hackathon getHackathonById(Long id);

    /**
     * Restituisce tutti gli hackathon in cui l'utente fa parte dello staff (organizzatore, giudice o mentore).
     *
     * @param userId l'ID del membro dello staff
     * @return lista degli hackathon associati
     */
    List<Hackathon> getAllMyHackathons(Long userId);

    /**
     * Aggiunge un mentore allo staff dell'hackathon.
     *
     * @param hackathonId l'ID dell'hackathon
     * @param userId l'ID dell'utente con ruolo MENTOR
     */
    void addMentor(Long hackathonId, Long userId);

    /**
     * Annulla un hackathon durante la fase di iscrizione.
     *
     * @param hackathonId l'ID dell'hackathon da annullare
     */
    void cancelHackathon(Long hackathonId);

    /**
     * Elimina definitivamente un hackathon concluso su richiesta dell'organizzatore.
     *
     * @param hackathonId l'ID dell'hackathon da eliminare
     * @param requestingUserId l'ID dell'organizzatore richiedente
     */
    void deleteHackathon(Long hackathonId, Long requestingUserId);
}
