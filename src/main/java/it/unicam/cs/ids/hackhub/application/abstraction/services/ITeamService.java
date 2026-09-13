package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.domain.model.Team;

/**
 * Servizio per la gestione dei team, creazione, gestione dei membri e scioglimento.
 */
public interface ITeamService {

    /**
     * Crea un nuovo team assegnando l'utente specificato come creatore/leader.
     *
     * @param teamName il nome del team
     * @param userId l'ID dell'utente creatore
     * @return l'entità Team creata
     */
    Team createTeam(String teamName, Long userId);

    /**
     * Elimina un team su richiesta del creatore.
     *
     * @param teamId l'ID del team da eliminare
     * @param requestingUserId l'ID dell'utente che richiede l'eliminazione
     */
    void deleteTeam(Long teamId, Long requestingUserId);

    /**
     * Rimuove un membro dal team.
     *
     * @param memberUserId l'ID dell'utente da rimuovere
     * @param teamId l'ID del team
     */
    void removeMember(Long memberUserId, Long teamId);

    /**
     * Permette a un membro di abbandonare volontariamente il proprio team.
     *
     * @param userId l'ID dell'utente che abbandona
     * @param teamId l'ID del team
     */
    void leaveTeam(Long userId, Long teamId);
}
