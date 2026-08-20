package it.unicam.cs.ids.hackhub.presentation.controllers;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IHackathonService;
import it.unicam.cs.ids.hackhub.application.dto.request.RegisterTeamRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hackathons")
public class HackathonController {
    private final IHackathonService hackathonService;

    public HackathonController(IHackathonService hackathonService) {
        this.hackathonService = hackathonService;
    }

    @PostMapping("/{id}/registrations")
    public ResponseEntity<Void> registerTeam(
            @PathVariable Long id,
            @RequestBody RegisterTeamRequest request) {
        hackathonService.registerTeamToHackathon(request.userId(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/registrations")
    public void unregisterTeam(
            @PathVariable Long id,
            @RequestParam Long userId) {
    }
}
