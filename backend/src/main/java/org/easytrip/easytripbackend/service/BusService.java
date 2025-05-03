package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BusRequestDTO;
import org.easytrip.easytripbackend.dto.BusResponseDTO;
import org.easytrip.easytripbackend.exception.UnauthorizedRoleException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Bus;
import org.easytrip.easytripbackend.model.BusOperator;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BusOperatorRepository;
import org.easytrip.easytripbackend.repository.BusRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusService {
    private static final Logger logger = LoggerFactory.getLogger(BusService.class);
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private BusOperatorRepository busOperatorRepository;

    @Autowired
    private UserRepository userRepository;

    public BusResponseDTO registerBus(BusRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Registering Bus by email: {}", email);

        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if(!user.getRole().contains(Role.BUS_OPERATOR)){
            logger.warn("User with email: {} is not a bus operator", email);
            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
        }

        BusOperator operator = busOperatorRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));

        String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocumentImage(), user.getId());
        Bus bus = new Bus();
        bus.setName(requestDTO.getName());
        bus.setBusType(requestDTO.getBusType());
        bus.setTotalSeats(requestDTO.getTotalSeats());
        bus.setOperator(operator);
        bus.setStatus(ApprovalStatus.PENDING);
        Bus savedBus =busRepository.save(bus);
        logger.info("Saved Bus with name: {} and id: {}", bus.getName(), savedBus.getId());
        return mapToDTO(savedBus);
    }

    public BusResponseDTO updateBus(BusRequestDTO requestDTO, Long busId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Updating Bus by email: {}", email);

        User currentUser= userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if(!currentUser.getRole().contains(Role.BUS_OPERATOR)){
            logger.warn("User with email: {} is not a bus operator", email);
            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
        }

        Bus existingBus= busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + busId));

        boolean isOwner= existingBus.getOperator().getUser().getId().equals(currentUser.getId());
        boolean isAdmin= currentUser.getRole().contains(Role.ADMIN);

        if(!isAdmin && !isOwner){
            logger.error("User with email: {} is neither an admin nor an owner", email);
            throw new UnauthorizedRoleException("User with email: " + email + " is not an admin nor an owner");
        }

        String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocumentImage(), currentUser.getId());
        existingBus.setName(requestDTO.getName());
        existingBus.setBusType(requestDTO.getBusType());
        existingBus.setTotalSeats(requestDTO.getTotalSeats());
        existingBus.setVerifiedDocumentImage(documentPath);
        existingBus.setOperator(existingBus.getOperator());
        existingBus.setStatus(ApprovalStatus.PENDING);

        Bus updatedBus = busRepository.save(existingBus);
        logger.info("Updated Bus with name: {} and id: {}", existingBus.getName(), updatedBus.getId());
        return mapToDTO(updatedBus);
    }

    public void deleteBus(Long busId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Deleting Bus by email: {}", email);

        User currentUser= userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if(!currentUser.getRole().contains(Role.BUS_OPERATOR)){
            logger.warn("User with email: {} is not a bus operator", email);
            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
        }

        Bus existingBus= busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + busId));

        boolean isOwner= existingBus.getOperator().getUser().getId().equals(currentUser.getId());
        boolean isAdmin= currentUser.getRole().contains(Role.ADMIN);

        if(!isAdmin && !isOwner){
            logger.error("User with email: {} is neither an admin nor an owner", email);
            throw new UnauthorizedRoleException("User with email: " + email + " is not an admin nor an owner");
        }
        Bus bus= new Bus();
        busRepository.delete(bus);
        logger.info("Deleted Bus with name: {} and id: {}", existingBus.getName(), busId);
    }

    public BusResponseDTO approveBus(Long busId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Approving Bus by email: {}", email);

        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if(!user.getRole().contains(Role.ADMIN)){
            logger.error("users with email: {} is not an admin", email);
            throw new UnauthorizedRoleException("users with email: " + email + " is not an admin");
        }

        Bus bus= busRepository.findById(busId)
                .orElseThrow(() -> new UserNotFoundException("Bus not found with id: " + busId));

        bus.setStatus(ApprovalStatus.APPROVED);
        Bus approvedBus = busRepository.save(bus);
        logger.info("Approved Bus with name: {} and id: {}", approvedBus.getName(), busId);
        return mapToDTO(approvedBus);
    }

    public BusResponseDTO rejectBus(Long busId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Rejecting Bus by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if(!user.getRole().contains(Role.ADMIN)){
            logger.error("users with email: {} is not an admin", email);
            throw new UnauthorizedRoleException("users with email: " + email + " is not an admin");
        }

        Bus bus= busRepository.findById(busId).
                orElseThrow(() -> new UserNotFoundException("Bus not found with id: " + busId));
        bus.setStatus(ApprovalStatus.REJECTED);
        Bus rejectedBus = busRepository.save(bus);
        logger.info("Rejected Bus with name: {} and id: {}", rejectedBus.getName(), busId);
        return mapToDTO(rejectedBus);
    }

    public List<BusResponseDTO> getAllBuses(){
        List<Bus> allBuses = busRepository.findAll();
        if(allBuses.isEmpty()){
            logger.error("No buses found");
            return Collections.emptyList();
        }
        return allBuses.stream().map(this:: mapToDTO).collect(Collectors.toList());
    }

    public BusResponseDTO getBus(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(()-> new UserNotFoundException("Bus not found with id: " + busId));

        return mapToDTO(bus);
    }

    private BusResponseDTO mapToDTO(Bus bus) {
        BusResponseDTO dto = new BusResponseDTO();
        dto.setId(bus.getId());
        dto.setName(bus.getName());
        dto.setBusType(bus.getBusType());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setStatus(bus.getStatus());
        dto.setOperatorId(bus.getOperator().getId());
        return dto;
    }

}
