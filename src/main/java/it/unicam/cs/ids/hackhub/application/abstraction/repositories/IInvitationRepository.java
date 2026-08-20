package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import it.unicam.cs.ids.hackhub.domain.model.Invitation;

import java.util.List;
import java.util.Optional;

public interface IInvitationRepository {
    Invitation save(Invitation invitation);

    Optional<Invitation> findById(Long id);

    List<Invitation> findPendingByUserId(Long userId);
}
