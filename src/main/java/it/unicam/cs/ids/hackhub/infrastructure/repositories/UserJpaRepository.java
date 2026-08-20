package it.unicam.cs.ids.hackhub.infrastructure.repositories;

import it.unicam.cs.ids.hackhub.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
