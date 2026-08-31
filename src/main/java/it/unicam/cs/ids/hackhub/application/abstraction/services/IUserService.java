package it.unicam.cs.ids.hackhub.application.abstraction.services;

import it.unicam.cs.ids.hackhub.application.dto.request.RegisterUserRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateProfileRequest;
import it.unicam.cs.ids.hackhub.domain.model.User;

public interface IUserService {

    User register(RegisterUserRequest request);

    boolean login(String email, String password);

    void logout(Long userId);

    User updateProfile(Long userId, UpdateProfileRequest request);

    void deleteUser(Long userId);

    User getUserById(Long userId);
}
