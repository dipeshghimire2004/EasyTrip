package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BusRequestDTO;
import org.easytrip.easytripbackend.dto.BusResponseDTO;
import org.easytrip.easytripbackend.exception.InvalidCredentialsException;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusService {
    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public BusResponseDTO registerBus(BusRequestDTO request, Long operatorId) {
        logger.info("Registering bus for operator ID: {}", operatorId);
        User operator = userRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        if (!operator.getRole().contains(Role.BUS_OPERATOR)) {
            throw new RuntimeException("User is not a bus operator");
        }

        String documentPath = fileStorageService.uploadFile(request.getVerifiedDocument(), operatorId);

        Bus bus = new Bus();
        bus.setName(request.getName());
        bus.setOwnerName(request.getOwnerName());
        bus.setOwnerPhone(request.getOwnerPhone());
        bus.setBusType(request.getBusType());
        bus.setTotalSeats(request.getTotalSeats());
        bus.setSource(request.getSource());
        bus.setDestination(request.getDestination());
        bus.setDepartureTime(request.getDepartureTime());
        bus.setArrivalTime(request.getArrivalTime());
        bus.setVerifiedDocumentImage(documentPath);
        bus.setDescription(request.getDescription());
        bus.setStatus(ApprovalStatus.PENDING);
        bus.setOperator(operator);

        Bus savedBus = busRepository.save(bus);
        return mapToResponse(savedBus);
    }

    public BusResponseDTO updateBus(Long busId, BusRequestDTO request, Long userId) {
        logger.info("Updating bus ID: {} by user ID: {}", busId, userId);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().contains(Role.ADMIN) && !bus.getOperator().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to update this bus");
        }

        bus.setName(request.getName());
        bus.setOwnerName(request.getOwnerName());
        bus.setOwnerPhone(request.getOwnerPhone());
        bus.setBusType(request.getBusType());
        bus.setTotalSeats(request.getTotalSeats());
        bus.setSource(request.getSource());
        bus.setDestination(request.getDestination());
        bus.setDepartureTime(request.getDepartureTime());
        bus.setArrivalTime(request.getArrivalTime());
        bus.setDescription(request.getDescription());
        // Note: verifiedDocumentImage is not updated here (file upload only during registration)

        Bus updatedBus = busRepository.save(bus);
        return mapToResponse(updatedBus);
    }

    public void deleteBus(Long busId, Long userId) {
        logger.info("Deleting bus ID: {} by user ID: {}", busId, userId);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().contains(Role.ADMIN) && !bus.getOperator().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this bus");
        }

        busRepository.delete(bus);
    }

    public BusResponseDTO approveBus(Long busId, Long adminId) {
        logger.info("Admin ID: {} approving bus ID: {}", adminId, busId);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getRole().contains(Role.ADMIN)) {
            throw new RuntimeException("User is not an admin");
        }

        bus.setStatus(ApprovalStatus.APPROVED);
        Bus updatedBus = busRepository.save(bus);
        return mapToResponse(updatedBus);
    }

    public BusResponseDTO denyBus(Long busId, Long adminId) {
        logger.info("Admin ID: {} denying bus ID: {}", adminId, busId);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getRole().contains(Role.ADMIN)) {
            throw new RuntimeException("User is not an admin");
        }

        bus.setStatus(ApprovalStatus.REJECTED);
        Bus updatedBus = busRepository.save(bus);
        return mapToResponse(updatedBus);
    }

    public List<BusResponseDTO> searchBuses(String source, String destination) {
        logger.info("Searching buses by source: {} and destination: {}", source, destination);
        List<Bus> buses = busRepository.findByStatus(ApprovalStatus.APPROVED);

        // Only include buses with a future departure time (operational buses)
        buses = buses.stream()
                .filter(b -> b.getDepartureTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        if (source != null && !source.isEmpty()) {
            buses = buses.stream()
                    .filter(b -> b.getSource().equalsIgnoreCase(source))
                    .collect(Collectors.toList());
        }

        if (destination != null && !destination.isEmpty()) {
            buses = buses.stream()
                    .filter(b -> b.getDestination().equalsIgnoreCase(destination))
                    .collect(Collectors.toList());
        }

        return buses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BusResponseDTO mapToResponse(Bus bus) {
        BusResponseDTO response = new BusResponseDTO();
        response.setId(bus.getId());
        response.setName(bus.getName());
        response.setOwnerName(bus.getOwnerName());
        response.setOwnerPhone(bus.getOwnerPhone());
        response.setBusType(bus.getBusType());
        response.setTotalSeats(bus.getTotalSeats());
        response.setSource(bus.getSource());
        response.setDestination(bus.getDestination());
        response.setDepartureTime(bus.getDepartureTime());
        response.setArrivalTime(bus.getArrivalTime());
        response.setVerifiedDocumentImage(bus.getVerifiedDocumentImage());
        response.setDescription(bus.getDescription());
        response.setStatus(bus.getStatus());
        response.setOperatorId(bus.getOperator().getId());
        return response;
    }
}
//
//    public BusResponseDTO registerBus(BusRequestDTO requestDTO) {
//
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Registering Bus by email: {}", email);
//
//        User user=userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//        if(!user.getRole().contains(Role.BUS_OPERATOR)){
//            logger.warn("User with email: {} is not a bus operator", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
//        }
//
//        // Find BusOperator by user ID
//        logger.debug("Fetching BusOperator for user ID: {}", user.getId());
//        BusOperator operator = busOperatorRepository.findByUserId(user.getId())
//                .orElseThrow(() -> new UserNotFoundException("Bus operator not found for user with email: " + email));
//        logger.debug("Found BusOperator with ID: {}", operator.getId());
//
//
//        String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocumentImage(), user.getId());
//        Bus bus = new Bus();
//        bus.setName(requestDTO.getName());
//        bus.setBusType(requestDTO.getBusType());
//        bus.setTotalSeats(requestDTO.getTotalSeats());
//        bus.setStatus(ApprovalStatus.PENDING);
//        bus.setVerifiedDocumentImage(documentPath);
//        bus.setOperator(operator);
//        Bus savedBus =busRepository.save(bus);
//        logger.info("Saved Bus with name: {} and id: {}", bus.getName(), savedBus.getId());
//        return mapToDTO(savedBus);
//    }

//    public BusResponseDTO updateBus(BusRequestDTO requestDTO, Long busId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Updating Bus by email: {}", email);
//
//        User currentUser= userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//        if(!currentUser.getRole().contains(Role.BUS_OPERATOR)){
//            logger.warn("User with email: {} is not a bus operator", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
//        }
//
//        Bus existingBus= busRepository.findById(busId)
//                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + busId));
//
//        boolean isOwner= existingBus.getOperator().getUser().getId().equals(currentUser.getId());
//        boolean isAdmin= currentUser.getRole().contains(Role.ADMIN);
//
//        if(!isAdmin && !isOwner){
//            logger.error("User with email: {} is neither an admin nor an owner", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not an admin nor an owner");
//        }
//
//        String documentPath = fileStorageService.uploadFile(requestDTO.getVerifiedDocumentImage(), currentUser.getId());
//        existingBus.setName(requestDTO.getName());
//        existingBus.setBusType(requestDTO.getBusType());
//        existingBus.setTotalSeats(requestDTO.getTotalSeats());
//        existingBus.setVerifiedDocumentImage(documentPath);
//        existingBus.setOperator(existingBus.getOperator());
//        existingBus.setStatus(ApprovalStatus.PENDING);
//
//        Bus updatedBus = busRepository.save(existingBus);
//        logger.info("Updated Bus with name: {} and id: {}", existingBus.getName(), updatedBus.getId());
//        return mapToDTO(updatedBus);
//    }
//
//    public void deleteBus(Long busId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Deleting Bus by email: {}", email);
//
//        User currentUser= userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//        if(!currentUser.getRole().contains(Role.BUS_OPERATOR)){
//            logger.warn("User with email: {} is not a bus operator", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
//        }
//
//        Bus existingBus= busRepository.findById(busId)
//                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + busId));
//
//        boolean isOwner= existingBus.getOperator().getUser().getId().equals(currentUser.getId());
//        boolean isAdmin= currentUser.getRole().contains(Role.ADMIN);
//
//        if(!isAdmin && !isOwner){
//            logger.error("User with email: {} is neither an admin nor an owner", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not an admin nor an owner");
//        }
//        Bus bus= new Bus();
//        busRepository.delete(bus);
//        logger.info("Deleted Bus with name: {} and id: {}", existingBus.getName(), busId);
//    }
//
//    public BusResponseDTO approveBus(Long busId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Approving Bus by email: {}", email);
//
//        User user= userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//        if(!user.getRole().contains(Role.ADMIN)){
//            logger.error("users with email: {} is not an admin", email);
//            throw new UnauthorizedRoleException("users with email: " + email + " is not an admin");
//        }
//
//        Bus bus= busRepository.findById(busId)
//                .orElseThrow(() -> new UserNotFoundException("Bus not found with id: " + busId));
//
//        bus.setStatus(ApprovalStatus.APPROVED);
//        Bus approvedBus = busRepository.save(bus);
//        logger.info("Approved Bus with name: {} and id: {}", approvedBus.getName(), busId);
//        return mapToDTO(approvedBus);
//    }
//
//    public BusResponseDTO rejectBus(Long busId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Rejecting Bus by email: {}", email);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//        if(!user.getRole().contains(Role.ADMIN)){
//            logger.error("users with email: {} is not an admin", email);
//            throw new UnauthorizedRoleException("users with email: " + email + " is not an admin");
//        }
//
//        Bus bus= busRepository.findById(busId).
//                orElseThrow(() -> new UserNotFoundException("Bus not found with id: " + busId));
//        bus.setStatus(ApprovalStatus.REJECTED);
//        Bus rejectedBus = busRepository.save(bus);
//        logger.info("Rejected Bus with name: {} and id: {}", rejectedBus.getName(), busId);
//        return mapToDTO(rejectedBus);
//    }
//
//    public List<BusResponseDTO> getAllBuses(){
//        List<Bus> allBuses = busRepository.findAll();
//        if(allBuses.isEmpty()){
//            logger.error("No buses found");
//            return Collections.emptyList();
//        }
//        return allBuses.stream().map(this:: mapToDTO).collect(Collectors.toList());
//    }
//
//    public BusResponseDTO getBus(Long busId) {
//        Bus bus = busRepository.findById(busId)
//                .orElseThrow(()-> new UserNotFoundException("Bus not found with id: " + busId));
//
//        return mapToDTO(bus);
//    }


//    private BusResponseDTO mapToDTO(Bus bus) {
//        BusResponseDTO dto = new BusResponseDTO();
//        dto.setId(bus.getId());
//        dto.setName(bus.getName());
//        dto.setBusType(bus.getBusType());
//        dto.setTotalSeats(bus.getTotalSeats());
//        dto.setStatus(bus.getStatus());
//        dto.setOperatorId(bus.getOperator().getId());
//        return dto;
//    }

//}
