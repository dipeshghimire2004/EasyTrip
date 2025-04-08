package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.LoginRequest;
import org.easytrip.easytripbackend.dto.RegisterRequest;
import org.easytrip.easytripbackend.exception.InvalidCredentialsException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.easytrip.easytripbackend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            logger.error("Passwords do not match for email: {}", request.getEmail());
            throw new InvalidCredentialsException("Passwords do not match");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("User with email: {} already exists", request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        String roleStr = request.getRole() != null ? request.getRole().trim() : "CLIENT";
        logger.info("Requested role: '{}'", roleStr);
        logger.info("Available roles: {}", Arrays.toString(Role.values()));

        Role role;
        try {
            role = Role.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleStr + ". Available roles: " + Arrays.toString(Role.values()));
        }
        user.setRole(Collections.singleton(role));

        logger.info("User registered successfully: {}", user.getEmail());
        return userRepository.save(user);
    }

    public Map<String, String> login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            Set<String> roles = user.getRole().stream().map(Enum::name).collect(Collectors.toSet());
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", jwtUtil.generateToken(user.getEmail(), roles));
            tokens.put("refreshToken", jwtUtil.generateRefreshToken(user.getEmail()));
            logger.info("Login successful for email: {}", request.getEmail());
            logger.info("Generated tokens: {}", tokens);
            return tokens;
        } catch (Exception e) {
            logger.error("Invalid credentials for email: {}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    public String refreshToken(String refreshToken) {
        logger.info("Refreshing token");
        String newToken = jwtUtil.refreshAccessToken(refreshToken);
        logger.info("Refresh token successful");
        return newToken;
    }

    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole().contains(Role.ADMIN)) {
            throw new RuntimeException("Cannot deactivate an admin user");
        }
        user.setActive(false);
        userRepository.save(user);
    }

    public void activateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setActive(true);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getActiveUsers(boolean isActive) {
        return userRepository.findByIsActive(isActive);
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}