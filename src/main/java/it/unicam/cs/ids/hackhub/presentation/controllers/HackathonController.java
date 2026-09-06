package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IHackathonService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.HackathonMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.RegisterTeamRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.HackathonResponse;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

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
    public ResponseEntity<Void> unregisterTeam(
            @PathVariable Long id,
            @RequestParam Long userId) {
        hackathonService.unregisterTeamFromHackathon(userId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HackathonResponse>> getAllHackathons() {
        List<Hackathon> list = hackathonService.getAllHackathons();
        List<HackathonResponse> responses = new ArrayList<>(list.size());
        for (Hackathon h : list) {
            responses.add(HackathonMapper.toResponse(h));
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HackathonResponse> getHackathonById(@PathVariable Long id) {
        Hackathon hackathon = hackathonService.getHackathonById(id);
        return ResponseEntity.ok(HackathonMapper.toResponse(hackathon));
    }

    @GetMapping("/staff/{userId}")
    public ResponseEntity<List<HackathonResponse>> getAllMyHackathons(@PathVariable Long userId) {
        List<Hackathon> list = hackathonService.getAllMyHackathons(userId);
        List<HackathonResponse> responses = new ArrayList<>(list.size());
        for (Hackathon h : list) {
            responses.add(HackathonMapper.toResponse(h));
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/mentors")
    public ResponseEntity<Void> addMentor(
            @PathVariable Long id,
            @RequestParam Long userId) {
        hackathonService.addMentor(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelHackathon(@PathVariable Long id) {
        hackathonService.cancelHackathon(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHackathon(
            @PathVariable Long id,
            @RequestParam Long requestingUserId) {
        hackathonService.deleteHackathon(id, requestingUserId);
        return ResponseEntity.noContent().build();
    }
}
