package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.application.dto.request.RegisterUserRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateProfileRequest;
import it.unicam.cs.ids.hackhub.domain.model.User;

/**
 * Servizio per l'autenticazione, la registrazione e la gestione del profilo utente.
 */
public interface IUserService {

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param request i dati di registrazione dell'utente
     * @return l'entità User creata e persistita
     */
    User register(RegisterUserRequest request);

    /**
     * Autentica un utente tramite email e password.
     *
     * @param email l'indirizzo email dell'utente
     * @param password la password in chiaro
     * @return true se le credenziali sono valide, false altrimenti
     */
    boolean login(String email, String password);

    /**
     * Effettua il logout dell'utente specificato.
     *
     * @param userId l'ID dell'utente che effettua il logout
     */
    void logout(Long userId);

    /**
     * Aggiorna i dati anagrafici o di contatto dell'utente.
     *
     * @param userId l'ID dell'utente da aggiornare
     * @param request i nuovi dati del profilo
     * @return l'entità User aggiornata
     */
    User updateProfile(Long userId, UpdateProfileRequest request);

    /**
     * Elimina l'account dell'utente specificato.
     *
     * @param userId l'ID dell'utente da eliminare
     */
    void deleteUser(Long userId);

    /**
     * Recupera un utente tramite il suo identificativo univoco.
     *
     * @param userId l'ID dell'utente
     * @return l'entità User corrispondente
     */
    User getUserById(Long userId);
}
