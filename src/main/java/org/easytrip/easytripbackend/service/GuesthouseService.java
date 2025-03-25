package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.GuesthouseRequestDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.exception.InvalidCredentialsException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.GuesthouseApprovalStatus;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GuesthouseService {
    @Autowired
    private GuesthouseRepository guesthouseRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public GuesthouseResponseDTO registerGuesthouse(GuesthouseRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!owner.getRoles().contains(Role.HOTEL_MANAGER)){
           throw new InvalidCredentialsException("User must have ROLE_HOTEL_MANAGER");
        }
        Guesthouse guestHouse = new Guesthouse();
        guestHouse.setName(requestDTO.getName());
        guestHouse.setDescription(requestDTO.getDescription());
        guestHouse.setOwner(owner);
        guestHouse.setLocation(requestDTO.getLocation());
        guestHouse.setVerifiedDocument(requestDTO.getVerifiedDocument());

        Guesthouse savedGuestHouse = guesthouseRepository.save(guestHouse);

        return mapToResponse(savedGuestHouse);
    }

    public GuesthouseResponseDTO approveGuesthouse(long guesthouseId) {
        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
        guesthouse.setStatus(GuesthouseApprovalStatus.APPROVED);
        Guesthouse saved = guesthouseRepository.save(guesthouse);
        //TODO : Send Email verfication to owner
        return mapToResponse(saved);
    }

    public GuesthouseResponseDTO rejectGuesthouse(long guesthouseId) {
        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
        guesthouse.setStatus(GuesthouseApprovalStatus.REJECTED);
        Guesthouse saved = guesthouseRepository.save(guesthouse);
        // TODO: Send email notification to owner
        return mapToResponse(saved);
    }


    private GuesthouseResponseDTO mapToResponse(Guesthouse guestHouse) {
        GuesthouseResponseDTO responseDTO = new GuesthouseResponseDTO();
        responseDTO.setId(guestHouse.getId());
        responseDTO.setName(guestHouse.getName());
        responseDTO.setLocation(guestHouse.getLocation());
        responseDTO.setContactDetails(guestHouse.getContactDetails());
        responseDTO.setVerifiedDocument(guestHouse.getVerifiedDocument());
        responseDTO.setDescription(guestHouse.getDescription());
        responseDTO.setStatus(guestHouse.getStatus().name());
        return responseDTO;
    }
}
