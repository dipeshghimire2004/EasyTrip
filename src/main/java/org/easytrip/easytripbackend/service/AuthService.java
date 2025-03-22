package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.LoginRequest;
import org.easytrip.easytripbackend.dto.RegisterRequest;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.easytrip.easytripbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.CLIENT));
        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
    User user= userRepository.findByEmail(request.getEmail()).
            orElseThrow(()-> new RuntimeException("User not found"));
    if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
        throw new RuntimeException("Passwords do not match");
    }
        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
    }

}
