package it.unicam.cs.ids.hackhub.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
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
import it.unicam.cs.ids.hackhub.application.dto.request.CreateHackathonRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.RegisterTeamRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.HackathonResponse;
import it.unicam.cs.ids.hackhub.domain.model.Hackathon;

/**
 * Controller per la gestione degli hackathon, creazione, iscrizione/disiscrizione team e staff.
 */
@RestController
@RequestMapping("/api/hackathons")
public class HackathonController {

    private final IHackathonService hackathonService;

    public HackathonController(IHackathonService hackathonService) {
        this.hackathonService = hackathonService;
    }

    /**
     * Crea un nuovo hackathon.
     *
     * @param request DTO contenente i dati di configurazione dell'hackathon
     * @return 201 Created con l'HackathonResponse creato
     */
    @PostMapping
    public ResponseEntity<HackathonResponse> createHackathon(@RequestBody CreateHackathonRequest request) {
        Hackathon created = hackathonService.createHackathon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(HackathonMapper.toResponse(created));
    }

    /**
     * Registra un team a un hackathon.
     *
     * @param id ID dell'hackathon
     * @param request DTO contenente l'ID dell'utente richiedente
     * @return 204 No Content se l'iscrizione è avvenuta con successo
     */
    @PostMapping("/{id}/registrations")
    public ResponseEntity<Void> registerTeam(
            @PathVariable Long id,
            @RequestBody RegisterTeamRequest request) {
        hackathonService.registerTeamToHackathon(request.userId(), id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Annulla l'iscrizione di un team a un hackathon.
     *
     * @param id ID dell'hackathon
     * @param userId ID dell'utente membro del team
     * @return 204 No Content se la cancellazione è avvenuta con successo
     */
    @DeleteMapping("/{id}/registrations")
    public ResponseEntity<Void> unregisterTeam(
            @PathVariable Long id,
            @RequestParam Long userId) {
        hackathonService.unregisterTeamFromHackathon(userId, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Restituisce tutti gli hackathon presenti nel sistema.
     *
     * @return lista dei DTO di risposta degli hackathon
     */
    @GetMapping
    public ResponseEntity<List<HackathonResponse>> getAllHackathons() {
        List<Hackathon> list = hackathonService.getAllHackathons();
        List<HackathonResponse> responses = new ArrayList<>(list.size());
        for (Hackathon h : list) {
            responses.add(HackathonMapper.toResponse(h));
        }
        return ResponseEntity.ok(responses);
    }

    /**
     * Recupera i dettagli di uno specifico hackathon.
     *
     * @param id ID dell'hackathon
     * @return DTO contenente i dettagli dell'hackathon
     */
    @GetMapping("/{id}")
    public ResponseEntity<HackathonResponse> getHackathonById(@PathVariable Long id) {
        Hackathon hackathon = hackathonService.getHackathonById(id);
        return ResponseEntity.ok(HackathonMapper.toResponse(hackathon));
    }

    /**
     * Restituisce gli hackathon a cui l'utente partecipa come membro dello staff.
     *
     * @param userId ID del membro dello staff
     * @return lista degli hackathon associati
     */
    @GetMapping("/staff/{userId}")
    public ResponseEntity<List<HackathonResponse>> getAllMyHackathons(@PathVariable Long userId) {
        List<Hackathon> list = hackathonService.getAllMyHackathons(userId);
        List<HackathonResponse> responses = new ArrayList<>(list.size());
        for (Hackathon h : list) {
            responses.add(HackathonMapper.toResponse(h));
        }
        return ResponseEntity.ok(responses);
    }

    /**
     * Assegna un mentore all'hackathon.
     *
     * @param id ID dell'hackathon
     * @param userId ID dell'utente con ruolo mentore
     * @return 204 No Content
     */
    @PostMapping("/{id}/mentors")
    public ResponseEntity<Void> addMentor(
            @PathVariable Long id,
            @RequestParam Long userId) {
        hackathonService.addMentor(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Annulla un hackathon nello stato REGISTRATION_OPEN.
     *
     * @param id ID dell'hackathon
     * @return 204 No Content
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelHackathon(@PathVariable Long id) {
        hackathonService.cancelHackathon(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina un hackathon concluso.
     *
     * @param id ID dell'hackathon
     * @param requestingUserId ID dell'organizzatore richiedente
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHackathon(
            @PathVariable Long id,
            @RequestParam Long requestingUserId) {
        hackathonService.deleteHackathon(id, requestingUserId);
        return ResponseEntity.noContent().build();
    }
}
