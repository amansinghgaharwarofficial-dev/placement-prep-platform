package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import com.example.demo.dto.ApiResponse;
import com.example.demo.security.JwtUtil;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER → save user with encoded password
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // LOGIN → check email + password
   @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody User loginUser) {

        if (loginUser.getEmail() == null || loginUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Email and password are required", 400, null));
        }

        Optional<User> optionalUser = userRepository.findByEmail(loginUser.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid email or password", 401, null));
        }

        User user = optionalUser.get();

        if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {

            String token = JwtUtil.generateToken(user.getEmail());

            return ResponseEntity.ok(
                    new ApiResponse<>("Login successful", 200, token)
            );

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Invalid email or password", 401, null));
        }
    }

    // GET → fetch all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}