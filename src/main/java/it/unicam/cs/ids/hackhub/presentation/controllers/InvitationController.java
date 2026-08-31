package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IInvitationService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.InvitationMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.SendInvitationRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.InvitationResponse;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final IInvitationService invitationService;

    public InvitationController(IInvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> sendInvitation(
            @RequestBody SendInvitationRequest request) {
        Invitation invitation = invitationService.sendInvitation(request.senderUserId(), request.targetUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InvitationMapper.toResponse(invitation));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectInvitation(@PathVariable Long id) {
        invitationService.rejectInvitation(id);
        return ResponseEntity.noContent().build();
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
