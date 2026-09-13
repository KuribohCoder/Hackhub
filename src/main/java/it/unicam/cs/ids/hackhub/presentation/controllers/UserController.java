package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IUserService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.UserMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.LoginRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.RegisterUserRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateProfileRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.UserResponse;
import it.unicam.cs.ids.hackhub.domain.model.User;

/**
 * Controller per la gestione degli account utente, autenticazione e profili.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param request DTO con i dati di registrazione
     * @return 201 Created con URI della risorsa creata e DTO utente
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {
        User user = userService.register(request);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId()))
                .body(UserMapper.toResponse(user));
    }

    /**
     * Esegue l'autenticazione tramite email e password.
     *
     * @param request DTO con credenziali di login
     * @return 200 OK se autenticato con successo, 401 UNAUTHORIZED altrimenti
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        boolean authenticated = userService.login(request.email(), request.password());
        if (!authenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Effettua il logout dell'utente.
     *
     * @param userId ID dell'utente
     * @return 200 OK
     */
    @PostMapping("/{userId}/logout")
    public ResponseEntity<Void> logout(@PathVariable Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Aggiorna i dati anagrafici o di contatto dell'utente.
     *
     * @param userId ID dell'utente da aggiornare
     * @param request DTO con i nuovi dati
     * @return 200 OK con DTO utente aggiornato
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long userId,
            @RequestBody UpdateProfileRequest request) {
        User user = userService.updateProfile(userId, request);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    /**
     * Elimina l'account dell'utente.
     *
     * @param userId ID dell'utente da eliminare
     * @return 204 No Content
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recupera le informazioni sul profilo di un utente per ID.
     *
     * @param userId ID dell'utente
     * @return 200 OK con DTO utente
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }
}
