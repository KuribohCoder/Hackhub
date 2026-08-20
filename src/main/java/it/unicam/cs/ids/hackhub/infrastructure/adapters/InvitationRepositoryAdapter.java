package it.unicam.cs.ids.hackhub.infrastructure.adapters;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IInvitationRepository;
import it.unicam.cs.ids.hackhub.domain.enums.InvitationStatus;
import it.unicam.cs.ids.hackhub.domain.model.Invitation;
import it.unicam.cs.ids.hackhub.infrastructure.repositories.InvitationJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InvitationRepositoryAdapter implements IInvitationRepository {
    private final InvitationJpaRepository jpaRepository;

    public InvitationRepositoryAdapter(InvitationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Invitation save(Invitation invitation) {
        return jpaRepository.save(invitation);
    }

    @Override
    public Optional<Invitation> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Invitation> findPendingByUserId(Long userId) {
        return jpaRepository.findByInvitedUser_IdAndStatus(userId, InvitationStatus.PENDING);
    }
}
