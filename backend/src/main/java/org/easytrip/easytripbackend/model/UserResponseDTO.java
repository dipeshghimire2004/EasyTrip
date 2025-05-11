package org.easytrip.easytripbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    Long id;
    private String name;
    private String email;
    private Set<String> roles;
}
