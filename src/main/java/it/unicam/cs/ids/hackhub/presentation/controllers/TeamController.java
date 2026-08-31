package it.unicam.cs.ids.hackhub.presentation.controllers;

import it.unicam.cs.ids.hackhub.application.abstraction.services.ITeamService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.TeamMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.CreateTeamRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.TeamResponse;
import it.unicam.cs.ids.hackhub.domain.model.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public void removeMember() {
    }

    @PostMapping("/{id}/leave")
    public void leaveTeam(
            @PathVariable Long id,
            @RequestParam Long userId) {
    }
}
