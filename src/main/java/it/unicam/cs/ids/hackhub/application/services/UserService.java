package it.unicam.cs.ids.hackhub.application.services;

import it.unicam.cs.ids.hackhub.application.abstraction.repositories.IUserRepository;
import it.unicam.cs.ids.hackhub.application.abstraction.services.IUserService;
import it.unicam.cs.ids.hackhub.application.dto.request.RegisterUserRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateProfileRequest;
import it.unicam.cs.ids.hackhub.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("I dati di registrazione non possono essere nulli.");
        }
        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("L'email non può essere vuota.");
        }
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("Email già registrata: " + request.email());
        }

        User user = new User(
                request.username(),
                request.name(),
                request.surname(),
                request.email(),
                request.password(),
                request.phone(),
                request.birthDate(),
                request.gender(),
                request.iban()
        );

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        return userRepository.findByEmail(email)
                .map(user -> password.equals(user.getPassword()))
                .orElse(false);
    }

    @Override
    public void logout(Long userId) {
    }

    @Override
    public User updateProfile(Long userId, UpdateProfileRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID utente non può essere null.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con id: " + userId));

        userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID utente non può essere null.");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con id: " + userId));
    }
}
