package com.kash.RealTimeChat.controller;

import com.kash.RealTimeChat.model.User;
import com.kash.RealTimeChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://cwave.netlify.com")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("Received registration request: " + (user != null ? user : "null"));
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Registration failed: Invalid user data (null or empty username)");
            return ResponseEntity.badRequest().body("Username is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("Registration failed: Email is required");
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            System.out.println("Registration failed: Password is required");
            return ResponseEntity.badRequest().body("Password is required");
        }
        if (userRepository.findByUsername(user.getUsername()) != null ) {
            System.out.println("Registration failed: Username " + user.getUsername() + " already exists");
            return ResponseEntity.ok(user);
        }
        try {
            User savedUser = userRepository.save(user);
            System.out.println("User registered successfully: " + savedUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        System.out.println("Fetching user: " + username);
        User user = userRepository.findByUsername(username);
        if (user != null){
            System.out.println("Found user: " + user);
                    return ResponseEntity.ok(user);
        }
        else{
            System.out.println("Found user: " + user);
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        System.out.println("hello from users");
        List<User> users = userRepository.findAll();
        System.out.println("Returning all users: " + users);
        return users;
    }
}