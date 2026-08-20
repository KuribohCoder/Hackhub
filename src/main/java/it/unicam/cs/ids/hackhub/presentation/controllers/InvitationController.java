package it.unicam.cs.ids.hackhub.presentation.controllers;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IInvitationService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.InvitationMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.SendInvitationRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.InvitationResponse;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final IInvitationService invitationService;

    public InvitationController(IInvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public void sendInvitation(
            @RequestBody SendInvitationRequest request) {
    }

    @PatchMapping("/{id}/reject")
    public void rejectInvitation(@PathVariable Long id) {
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvitationResponse>> getUserInvitations(@PathVariable Long userId) {
        List<Invitation> invitations = invitationService.getUserInvitations(userId);
        List<InvitationResponse> responseList = new ArrayList<>(invitations.size());

        for (Invitation invitation : invitations) {
            responseList.add(InvitationMapper.toResponse(invitation));
        }

        return ResponseEntity.ok(responseList);
    }
}
