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

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final ITeamService teamService;

    public TeamController(ITeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody CreateTeamRequest request) {
        Team team = teamService.createTeam(request.teamName(), request.userId());
        return ResponseEntity.created(URI.create("/api/teams/" + team.getId()))
                .body(TeamMapper.toResponse(team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long id,
            @RequestParam Long requestingUserId) {
        teamService.deleteTeam(id, requestingUserId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long id,
            @PathVariable Long userId) {
        teamService.removeMember(userId, id);
        return ResponseEntity.noContent().build();
    }
}
