package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.ITeamService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.TeamMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.CreateTeamRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.TeamResponse;
import it.unicam.cs.ids.hackhub.domain.model.Team;

/**
 * Controller per la creazione, gestione dei membri e scioglimento dei team.
 */
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final ITeamService teamService;

    public TeamController(ITeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Crea un nuovo team assegnando l'utente specificato come creatore.
     *
     * @param request DTO con nome del team e ID dell'utente creatore
     * @return 201 Created con URI del team e DTO di risposta
     */
    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody CreateTeamRequest request) {
        Team team = teamService.createTeam(request.teamName(), request.userId());
        return ResponseEntity.created(URI.create("/api/teams/" + team.getId()))
                .body(TeamMapper.toResponse(team));
    }

    /**
     * Elimina un team su richiesta del creatore.
     *
     * @param id ID del team
     * @param requestingUserId ID dell'utente creatore richiedente
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long id,
            @RequestParam Long requestingUserId) {
        teamService.deleteTeam(id, requestingUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Rimuove un membro dal team.
     *
     * @param id ID del team
     * @param userId ID del membro da rimuovere
     * @return 204 No Content
     */
    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long id,
            @PathVariable Long userId) {
        teamService.removeMember(userId, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Permette a un membro di lasciare volontariamente il team.
     *
     * @param id ID del team
     * @param userId ID dell'utente che abbandona
     * @return 204 No Content
     */
    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leaveTeam(
            @PathVariable Long id,
            @RequestParam Long userId) {
        teamService.leaveTeam(userId, id);
        return ResponseEntity.noContent().build();
    }
}
