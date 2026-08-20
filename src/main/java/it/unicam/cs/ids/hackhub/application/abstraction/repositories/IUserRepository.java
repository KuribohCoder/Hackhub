package it.unicam.cs.ids.hackhub.application.abstraction.repositories;

import it.unicam.cs.ids.hackhub.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);
}
