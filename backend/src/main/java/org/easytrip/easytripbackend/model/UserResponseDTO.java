package org.easytrip.easytripbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


public class UserResponseDTO {
    Long id;
    private String name;
    private String email;
    private String role;
    private Set<String> roles;

    public UserResponseDTO(Long id, String name, String email, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }
}
