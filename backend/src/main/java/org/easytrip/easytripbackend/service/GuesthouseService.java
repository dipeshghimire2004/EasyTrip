package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.GuesthouseRequestDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.exception.GuesthouseNotFoundException;
import org.easytrip.easytripbackend.exception.InvalidCredentialsException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.Room;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    private Room room;

    @Autowired
    private  FileStorageService fileStorageService;

    @Transactional
    public GuesthouseResponseDTO registerGuesthouse(GuesthouseRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Registering guesthouse by email: {}", email);
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!owner.getRole().contains(Role.HOTEL_MANAGER)) {
            logger.error("User with email {} has no role HOTEL_MANAGER", email);
            throw new InvalidCredentialsException("User must have ROLE_HOTEL_MANAGER");
        }

        String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocument(), owner.getId());
        Guesthouse guestHouse = new Guesthouse();
        guestHouse.setName(requestDTO.getName());
        guestHouse.setLocation(requestDTO.getLocation());
        guestHouse.setContactDetails(requestDTO.getContactDetails());
        guestHouse.setDescription(requestDTO.getDescription());
        guestHouse.setAmenities(requestDTO.getAmenitiesAsSet()); // Use converted Set
        guestHouse.setVerifiedDocumentImage(documentPath);
        guestHouse.setOwner(owner);
        guestHouse.setStatus(ApprovalStatus.PENDING);

        logger.info("Received amenities: {}", requestDTO.getAmenitiesAsSet());
        Guesthouse savedGuestHouse = guesthouseRepository.save(guestHouse);
        logger.info("Guesthouse registered with ID: {}", savedGuestHouse.getId());
        return mapToResponse(savedGuestHouse);
    }

    @Transactional
    public GuesthouseResponseDTO updateGuesthouse(GuesthouseRequestDTO requestDTO, Long guesthouseId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Updating guesthouse by email: {}", email);

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Guesthouse existingGuesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new GuesthouseNotFoundException("Guesthouse not found"));

        boolean isOwner = currentUser.getId().equals(existingGuesthouse.getOwner().getId());
        boolean isAdmin = currentUser.getRole().contains(Role.ADMIN);
        if (!isOwner && !isAdmin) {
            logger.error("User with email {} has no role ADMIN or HOTEL_MANAGER", email);
            throw new InvalidCredentialsException("User must have ROLE_ADMIN or ROLE_HOTEL_MANAGER");
        }

        if (requestDTO.getName() != null) {
            existingGuesthouse.setName(requestDTO.getName());
        }
        if (requestDTO.getLocation() != null) {
            existingGuesthouse.setLocation(requestDTO.getLocation());
        }
        if (requestDTO.getContactDetails() != null) {
            existingGuesthouse.setContactDetails(requestDTO.getContactDetails());
        }
        if (requestDTO.getDescription() != null) {
            existingGuesthouse.setDescription(requestDTO.getDescription());
        }
        if (requestDTO.getAmenities() != null) {
            existingGuesthouse.setAmenities(requestDTO.getAmenitiesAsSet());
        }
        if (requestDTO.getVerifiedDocument() != null && !requestDTO.getVerifiedDocument().isEmpty()) {
            String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocument(), currentUser.getId());
            existingGuesthouse.setVerifiedDocumentImage(documentPath);
            existingGuesthouse.setStatus(ApprovalStatus.PENDING);
        }

        if (isAdmin && requestDTO.getStatus() != null) {
            existingGuesthouse.setStatus(ApprovalStatus.valueOf(requestDTO.getStatus()));
        }

        Guesthouse updatedGuestHouse = guesthouseRepository.save(existingGuesthouse);
        logger.info("Guesthouse updated with ID: {}", updatedGuestHouse.getId());
        return mapToResponse(updatedGuestHouse);
    }


    // Get all pending guesthouses (for admin dashboard)
    public List<GuesthouseResponseDTO> getPendingGuesthouses() {
        logger.info("Fetching all pending guesthouses");
        List<Guesthouse> pending = guesthouseRepository.findByStatus(ApprovalStatus.PENDING);
        return pending.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<GuesthouseResponseDTO> getApprovedGuesthouses(){
        logger.info("Fetching all approved guesthouses");
        List<Guesthouse> approved = guesthouseRepository.findByStatus(ApprovalStatus.APPROVED);
        return approved.stream().map(this::mapToResponse).collect(Collectors.toList());
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

//    public GuesthouseResponseDTO approveGuesthouse(long guesthouseId) {
//        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
//                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
//        guesthouse.setStatus(ApprovalStatus.APPROVED);
//        Guesthouse saved = guesthouseRepository.save(guesthouse);
//        logger.info("Approved guesthouse with id {}" , guesthouseId);
//        //TODO : Send Email verfication to owner
//        return mapToResponse(saved);
//    }
    @Transactional
    public GuesthouseResponseDTO approveGuesthouse(long guesthouseId) {
        try {
            Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                    .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
            logger.info("Found guesthouse: {}", guesthouse);

            guesthouse.setStatus(ApprovalStatus.APPROVED);
            Guesthouse saved = guesthouseRepository.save(guesthouse);
            logger.info("Approved guesthouse with id {}", guesthouseId);

            return mapToResponse(saved);
        } catch (Exception e) {
            logger.error("Failed to approve guesthouse {}: {}", guesthouseId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public GuesthouseResponseDTO rejectGuesthouse(long guesthouseId) {
        try{
            Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                    .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
            guesthouse.setStatus(ApprovalStatus.REJECTED);
            Guesthouse saved = guesthouseRepository.save(guesthouse);
            logger.info("Rejected guesthouse with id {}" , guesthouseId);
            return mapToResponse(saved);
        }
        catch(Exception e){
            logger.error("Failed to reject guesthouse {}: {}", guesthouseId, e.getMessage(), e);
            throw e;
        }

    }

    public Guesthouse getGuesthouseById(long guesthouseId) {
        logger.info("Retrieving guesthouse with id {}" , guesthouseId);
        return guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
    }
    public Guesthouse getGuesthouseEntityById(Long guesthouseId) {
        return guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new RuntimeException("Guesthouse not found"));
    }

    public List<GuesthouseResponseDTO> searchGuesthouses(String location, String name) {
        logger.info("Searching guesthouses by location: {} and name: {}", location, name);
        List<Guesthouse> guesthouses;
        guesthouses= guesthouseRepository.findByStatus(ApprovalStatus.APPROVED);
        if(location!= null && !location.isEmpty()){
            guesthouses =guesthouses.stream().
                    filter(g -> g.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());

        }
        if(name!= null && !name.isEmpty()){
            guesthouses = guesthouses.stream()
                    .filter(g -> g.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
        }

        return guesthouses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GuesthouseResponseDTO mapToResponse(Guesthouse guesthouse) {
        GuesthouseResponseDTO responseDTO = new GuesthouseResponseDTO();
        responseDTO.setId(guesthouse.getId());
        responseDTO.setName(guesthouse.getName());
        responseDTO.setLocation(guesthouse.getLocation());
        responseDTO.setContactDetails(guesthouse.getContactDetails());
        responseDTO.setVerifiedDocument(guesthouse.getVerifiedDocumentImage());
        responseDTO.setDescription(guesthouse.getDescription());
        responseDTO.setAmenities(guesthouse.getAmenities());
        responseDTO.setStatus(guesthouse.getStatus().name());
//        responseDTO.setPricePerNight(room.getPricePerNight());
        responseDTO.setOwnerId(guesthouse.getOwner().getId());
        responseDTO.setOwnerName(guesthouse.getOwner().getName());
        return responseDTO;
    }
}
