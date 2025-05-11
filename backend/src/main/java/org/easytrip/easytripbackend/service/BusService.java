package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BusRequestDTO;
import org.easytrip.easytripbackend.dto.BusResponseDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.exception.InvalidCredentialsException;
import org.easytrip.easytripbackend.exception.UnauthorizedRoleException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Bus;
import org.easytrip.easytripbackend.model.BusOperator;
import org.easytrip.easytripbackend.model.Guesthouse;
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



import java.time.LocalDateTime;
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

    public BusResponseDTO registerBus(BusRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Registering bus by email: {}", email);
        User operator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!operator.getRole().contains(Role.BUS_OPERATOR)) {
            logger.error("User with email {} is not a BUS_OPERATOR", email);
            throw new RuntimeException("User must be a BUS_OPERATOR");
        }

        String documentPath = fileStorageService.uploadFile(request.getVerifiedDocument(), operator.getId());
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

        String documentPath = fileStorageService.uploadFile(request.getVerifiedDocument(), userId);
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


    public List<BusResponseDTO> getPendingBuses(){
        logger.info("Fetching all the pending buses");
        List<Bus> pendingBuses = busRepository.findByStatus(ApprovalStatus.PENDING);
        return pendingBuses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<BusResponseDTO> getApprovedBuses(){
        logger.info("Fetching all the approved buses");
        List<Bus> approvedBuses = busRepository.findByStatus(ApprovalStatus.APPROVED);
        return approvedBuses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<BusResponseDTO> searchBuses(String source, String destination) {
        logger.info("Searching buses by source: {} and destination: {}", source, destination);
        List<Bus> buses = busRepository.findByStatus(ApprovalStatus.APPROVED);

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
        response.setStatus(bus.getStatus());
        response.setOperatorId(bus.getOperator().getId());
        return response;
    }
}
