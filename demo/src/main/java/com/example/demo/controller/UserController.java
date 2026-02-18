package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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
    public String login(@RequestBody User loginUser) {

        User user = userRepository.findByEmail(loginUser.getEmail())
                  .orElse(null);

        if (user == null) {
            return "USER NOT FOUND";
        }

        if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return "LOGIN SUCCESS";
        } else {
            return "INVALID PASSWORD";
        }
    }

    // GET → fetch all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}