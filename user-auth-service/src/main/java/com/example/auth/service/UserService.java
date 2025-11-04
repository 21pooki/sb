package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.dto.UserLoginDto;
import com.example.auth.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            return "Username already taken!";
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            return "Email already registered!";
        }

        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword())); // Hash password

        userRepository.save(newUser);
        return "Registration successful!";
    }

    public String loginUser(UserLoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

        if (userOptional.isEmpty()) {
            return "Invalid username or password!";
        }

        User user = userOptional.get();
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid username or password!";
        }
    }
}