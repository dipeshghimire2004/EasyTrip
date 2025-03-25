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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            logger.error("Passwords do not match for email : {}", request.getEmail());
            throw new InvalidCredentialsException("Passwords do not match");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("User with email : {} already exists", request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.CLIENT));

        logger.info("User registered Successfully: {}", user.getEmail() );
        return userRepository.save(user);
    }

    public Map<String, String> login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
    User user= userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> {
            logger.error("User not found: {}", request.getEmail());
            return new UserNotFoundException("User not found");
        });
    if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
        logger.error("Invalid password for email: {}", request.getEmail());
        throw new InvalidCredentialsException("Passwords do not match");
    }
    Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        Map<String, String> tokens= new HashMap<>();
        tokens.put("accessToken", jwtUtil.generateToken(user.getEmail(), roles));
        tokens.put("refreshToken", jwtUtil.generateRefreshToken(user.getEmail()));
        logger.info("Login successful for email: {}", request.getEmail());
        return tokens;
    }

    public String refreshToken(String refreshToken) {
        logger.info("Refreshing token for refresh token");
        String newToken = jwtUtil.refreshAccessToken(refreshToken);
        logger.info("Refresh token successful");
        return newToken;
    }
}
