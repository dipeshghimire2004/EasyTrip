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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuesthouseService {
    private static final Logger logger = LoggerFactory.getLogger(GuesthouseService.class);

    @Autowired
    private GuesthouseRepository guesthouseRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private  FileStorageService fileStorageService;

    public GuesthouseResponseDTO registerGuesthouse(GuesthouseRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Registering guesthouse by email: {}" , email);
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!owner.getRole().contains(Role.HOTEL_MANAGER)){
            logger.error("User with email {} has no role HOTEL_MANAGER", email);
           throw new InvalidCredentialsException("User must have ROLE_HOTEL_MANAGER");
        }
        String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocument(), owner.getId());
        Guesthouse guestHouse = new Guesthouse();
        guestHouse.setName(requestDTO.getName());
        guestHouse.setLocation(requestDTO.getLocation());
        guestHouse.setContactDetails(requestDTO.getContactDetails());
        guestHouse.setDescription(requestDTO.getDescription());
        guestHouse.setVerifiedDocument(documentPath);
        guestHouse.setOwner(owner);

        Guesthouse savedGuestHouse = guesthouseRepository.save(guestHouse);
        logger.info("Guesthouse registered with ID: {}", savedGuestHouse.getId());
        return mapToResponse(savedGuestHouse);
    }

    // Get all pending guesthouses (for admin dashboard)
    public List<GuesthouseResponseDTO> getPendingGuesthouses() {
        logger.info("Fetching all pending guesthouses");
        List<Guesthouse> pending = guesthouseRepository.findByStatus(GuesthouseApprovalStatus.PENDING);
        return pending.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    //Get guesthouse by ID
    public GuesthouseResponseDTO getGuesthouseById(Long id) {
        logger.info("Fetching guesthouse by ID: {}", id);
        Guesthouse guesthouse= guesthouseRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToResponse(guesthouse);
    }
    //Get all guesthouse
    public List<GuesthouseResponseDTO> getAllGuesthouses() {
        logger.info("Fetching all guesthouses");
        List<Guesthouse> guesthouses = guesthouseRepository.findAll();
        if(guesthouses.isEmpty()){
            logger.warn("No guesthouses found");
            return Collections.emptyList();
        }
        return guesthouses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public GuesthouseResponseDTO approveGuesthouse(long guesthouseId) {
        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
        guesthouse.setStatus(GuesthouseApprovalStatus.APPROVED);
        Guesthouse saved = guesthouseRepository.save(guesthouse);
        logger.info("Approved guesthouse with id {}" , guesthouseId);
        //TODO : Send Email verfication to owner
        return mapToResponse(saved);
    }

    public GuesthouseResponseDTO rejectGuesthouse(long guesthouseId) {
        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
        guesthouse.setStatus(GuesthouseApprovalStatus.REJECTED);
        Guesthouse saved = guesthouseRepository.save(guesthouse);
        logger.info("Rejected guesthouse with id {}" , guesthouseId);
        // TODO: Send email notification to owner
        return mapToResponse(saved);
    }

    public Guesthouse getGuesthouseById(long guesthouseId) {
        logger.info("Retrieving guesthouse with id {}" , guesthouseId);
        return guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
    }

    public List<GuesthouseResponseDTO> searchGuesthouses(String location, String name) {
        logger.info("Searching guesthouses by location: {} and name: {}", location, name);
        List<Guesthouse> guesthouses;
        guesthouses= guesthouseRepository.findByStatus(GuesthouseApprovalStatus.APPROVED);
        if(location!= null || !location.isEmpty()){
            guesthouses =guesthouses.stream().
                    filter(g -> g.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());
        }
        if(name!= null || !name.isEmpty()){
            guesthouses = guesthouses.stream()
                    .filter(g -> g.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
        }

        return guesthouses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
