package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import java.util.List;

/**
 * Servizio per la gestione degli inviti di partecipazione ai team.
 */
public interface IInvitationService {

    /**
     * Invia un invito per entrare nel team del mittente all'utente target.
     *
     * @param senderUserId l'ID dell'utente mittente (membro di un team)
     * @param targetUserId l'ID dell'utente destinatario
     * @return l'entità Invitation creata
     */
    Invitation sendInvitation(Long senderUserId, Long targetUserId);

    /**
     * Rifiuta un invito ricevuto.
     *
     * @param invitationId l'ID dell'invito da rifiutare
     */
    void rejectInvitation(Long invitationId);

    /**
     * Recupera tutti gli inviti pendenti indirizzati a un utente.
     *
     * @param userId l'ID dell'utente destinatario
     * @return lista degli inviti associati
     */
    List<Invitation> getUserInvitations(Long userId);
}
