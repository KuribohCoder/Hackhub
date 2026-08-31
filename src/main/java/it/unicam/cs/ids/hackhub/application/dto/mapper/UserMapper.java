package it.unicam.cs.ids.hackhub.application.dto.mapper;

import it.unicam.cs.ids.hackhub.application.dto.response.UserResponse;
import it.unicam.cs.ids.hackhub.domain.model.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthDate(),
                user.getGender(),
                user.getIban(),
                user.getRole() != null ? user.getRole().name() : null,
                user.getTeam() != null ? user.getTeam().getId() : null,
                user.getCreatedAt()
        );
    }
}
