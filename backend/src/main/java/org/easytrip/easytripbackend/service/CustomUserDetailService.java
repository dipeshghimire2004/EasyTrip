package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email :"+email));
        if(!user.isActive()) {
            throw new UsernameNotFoundException("User not found with email :"+email);
        }

//        String[] rolesName= user.getRole()
//                .stream().map(role-> role.name())
//                .toArray(String[]::new);
//
        String[] rolesName= user.getRole()
                .stream().map(Enum::name)
                .toArray(String[]::new);

        if(!user.isActive()) {
            throw new UsernameNotFoundException("User not found/ is not active with email :"+email);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(rolesName)
                .build();
    }
}
