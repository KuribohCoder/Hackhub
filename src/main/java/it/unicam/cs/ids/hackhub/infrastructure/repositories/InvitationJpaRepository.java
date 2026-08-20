package it.unicam.cs.ids.hackhub.infrastructure.repositories;

import it.unicam.cs.ids.hackhub.domain.enums.InvitationStatus;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationJpaRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByInvitedUser_IdAndStatus(Long userId, InvitationStatus status);
}
