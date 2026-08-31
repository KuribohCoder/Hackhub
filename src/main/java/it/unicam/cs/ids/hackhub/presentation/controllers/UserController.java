package it.unicam.cs.ids.hackhub.presentation.controllers;

import it.unicam.cs.ids.hackhub.application.abstraction.services.IUserService;
import it.unicam.cs.ids.hackhub.application.dto.mapper.UserMapper;
import it.unicam.cs.ids.hackhub.application.dto.request.LoginRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.RegisterUserRequest;
import it.unicam.cs.ids.hackhub.application.dto.request.UpdateProfileRequest;
import it.unicam.cs.ids.hackhub.application.dto.response.UserResponse;
import it.unicam.cs.ids.hackhub.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {
        User user = userService.register(request);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId()))
                .body(UserMapper.toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        boolean authenticated = userService.login(request.email(), request.password());
        if (!authenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/logout")
    public void logout(@PathVariable Long userId) {
    }

    @PutMapping("/{userId}")
    public void updateProfile(
            @PathVariable Long userId,
            @RequestBody UpdateProfileRequest request) {
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }
}
