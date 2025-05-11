package org.easytrip.easytripbackend.service;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.easytrip.easytripbackend.dto.BusBookingRequestDTO;
import org.easytrip.easytripbackend.dto.BusBookingResponseDTO;
import org.easytrip.easytripbackend.exception.UnauthorizedRoleException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Booking;
import org.easytrip.easytripbackend.model.Bus;
import org.easytrip.easytripbackend.model.BusBooking;
import org.easytrip.easytripbackend.model.BusSchedule;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BusBookingRepository;
import org.easytrip.easytripbackend.repository.BusRepository;
import org.easytrip.easytripbackend.repository.BusScheduleRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusBookingService {
        private static final Logger logger = LoggerFactory.getLogger(BusBookingService.class);

        @Autowired
        private BusBookingRepository busBookingRepository;

        @Autowired
        private BusRepository busRepository;

        @Autowired
        private UserRepository userRepository;

        public BusBookingResponseDTO bookBus(BusBookingRequestDTO request, Long clientId) {
            logger.info("Client ID: {} booking bus ID: {}", clientId, request.getBusId());
            User client = userRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            if (!client.getRole().equals(Role.CLIENT)) {
                throw new RuntimeException("User is not a client");
            }

            Bus bus = busRepository.findById(request.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));

            if (!bus.getStatus().equals(ApprovalStatus.APPROVED)) {
                throw new RuntimeException("Bus is not approved");
            }

            if (bus.getDepartureTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Bus is not operational (departure time has passed)");
            }

            if (request.getSeatsBooked() > bus.getTotalSeats()) {
                throw new RuntimeException("Not enough seats available");
            }

            BusBooking booking = new BusBooking();
            booking.setClient(client);
            booking.setBus(bus);
            booking.setSeatsBooked(request.getSeatsBooked());
            booking.setBookingTime(LocalDateTime.now());
            booking.setCancelled(false);

            BusBooking savedBooking = busBookingRepository.save(booking);
            return mapToResponse(savedBooking);
        }

        public BusBookingResponseDTO cancelBooking(Long bookingId, Long clientId) {
            logger.info("Client ID: {} cancelling booking ID: {}", clientId, bookingId);
            BusBooking booking = busBookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            User client = userRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            if(!booking.getClient().equals(client)) {
                logger.info("Client ID: {} cancelling booking ID: {}", clientId, bookingId);
                throw new RuntimeException("Booking is not a client");
            }
//            if (!booking.getClient().getId().equals(client- orElseThrow(() -> new RuntimeException("Unauthorized to cancel this booking");

            booking.setCancelled(true);
            BusBooking updatedBooking = busBookingRepository.save(booking);
            return mapToResponse(updatedBooking);
        }

        public List<BusBookingResponseDTO> getClientBookings(Long clientId) {
            logger.info("Fetching bookings for client ID: {}", clientId);
            return busBookingRepository.findByClientIdAndIsCancelledFalse(clientId)
                    .stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        private BusBookingResponseDTO mapToResponse(BusBooking booking) {
            BusBookingResponseDTO response = new BusBookingResponseDTO();
            response.setId(booking.getId());
            response.setClientId(booking.getClient().getId());
            response.setBusId(booking.getBus().getId());
            response.setBookingTime(booking.getBookingTime());
            response.setSeatsBooked(booking.getSeatsBooked());
            response.setCancelled(booking.isCancelled());
            return response;
        }
    }


//    private static final Logger logger = LoggerFactory.getLogger(BusBookingService.class);
//    @Autowired
//    private BusBookingRepository busBookingRepository;
//
//    @Autowired
//    private BusScheduleRepository busScheduleRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BusNotificationService busNotificationService;
//
//    @Transactional
//    public BusBookingResponseDTO bookBus(BusBookingRequestDTO requestDTO) {
//        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Booking bus by {}", userEmail);
//
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(()-> new UserNotFoundException("User with email " + userEmail + " not found"));
//        if(!user.getRole().contains(Role.CLIENT)){
//            logger.warn("User {} is a not client", userEmail);
//            throw new UserNotFoundException("User " + userEmail + " is not a client");
//        }
//
//        BusSchedule schedule = busScheduleRepository.findById(requestDTO.getScheduleId())
//                .orElseThrow(()-> new RuntimeException("Schedule not found"));
//        if(schedule.getAvailableSeats() < requestDTO.getNumberOfSeats()){
//            logger.warn("Not enough seats available for schedule {}", schedule.getId());
//            throw new UserNotFoundException("Schedule " + schedule.getId() + " is not enough available");
//        }
//        BusBooking booking = new BusBooking();
//        booking.setSchedule(schedule);
//        booking.setClient(user);
//        booking.setNumberOfSeats(schedule.getAvailableSeats());
//        booking.setTotalFare(requestDTO.getNumberOfSeats() * schedule.getFare());
//        booking.setBookingTime(LocalDateTime.now());
//        booking.setCancelled(false);
//
//        String ownerEmail = schedule.getBus().getOperator().getUser().getEmail();
//        busNotificationService.sendBookingNotification(
//                user.getEmail(),
//                ownerEmail,
//                schedule.getBus().getName(),
//                userEmail,
//                schedule.getSource(),
//                schedule.getDestination(),
//                schedule.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                schedule.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                booking.getNumberOfSeats(),
//                booking.getTotalFare()
//        );
//
//        schedule.setAvailableSeats(schedule.getAvailableSeats() - requestDTO.getNumberOfSeats());
//        busScheduleRepository.save(schedule);
//        BusBooking savedBooking = busBookingRepository.save(booking);
//
//
//        logger.info("Booked bus with booking id :{}  for schedule id: {}", savedBooking.getId(), schedule.getId());
//        return mapToResponseDTO(savedBooking);
//    }
//
//    @Transactional
//    public void cancelBooking(Long bookingId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Cancelling booking by email: {}", email);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
//        if (!user.getRole().contains(Role.CLIENT)) {
//            logger.warn("User with email: {} is not a client", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not a client");
//        }
//
//        BusBooking booking = busBookingRepository.findById(bookingId)
//                .orElseThrow(() -> new UserNotFoundException("Booking with id: " + bookingId + " not found"));
//        if (!booking.getClient().getId().equals(user.getId())) {
//            logger.error("User with email: {} does not own booking id: {}", email, bookingId);
//            throw new UnauthorizedRoleException("User does not own this booking");
//        }
//        if (booking.isCancelled()) {
//            logger.warn("Booking with id: {} is already cancelled", bookingId);
//            throw new IllegalStateException("Booking is already cancelled");
//        }
//
//        BusSchedule schedule = booking.getSchedule();
//        schedule.setAvailableSeats(schedule.getAvailableSeats() + booking.getNumberOfSeats());
//        busScheduleRepository.save(schedule);
//
//        booking.setCancelled(true);
//        busBookingRepository.save(booking);
//
//        // Send cancellation notifications
//        String ownerEmail = schedule.getBus().getOperator().getUser().getEmail();
//        String passengerName = user.getEmail(); // Use email as name, or replace with actual name field if available
//        busNotificationService.sendCancellationNotification(
//                user.getEmail(),
//                ownerEmail,
//                schedule.getBus().getName(),
//                passengerName,
//                schedule.getSource(),
//                schedule.getDestination(),
//                schedule.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
//                schedule.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
//        );
//
//        logger.info("Cancelled booking with id: {}", bookingId);
//    }
//
//    public List<BusBookingResponseDTO> getAllBusBookings() {
//        logger.info("Fetching all bus bookings");
//        List<BusBooking> bookings = busBookingRepository.findAll();
//        if(bookings.isEmpty()){
//            logger.warn("No bookings found for this bus.");
//            return Collections.emptyList();
//        }
//        return bookings.stream().map(booking -> mapToResponseDTO(booking)).collect(Collectors.toList());
//    }
//
//    private BusBookingResponseDTO mapToResponseDTO(BusBooking busBooking) {
//        BusBookingResponseDTO responseDTO = new BusBookingResponseDTO();
//        responseDTO.setId(busBooking.getId());
//        responseDTO.setScheduleId(busBooking.getSchedule().getId());
//        responseDTO.setNumberOfSeats(busBooking.getNumberOfSeats());
//        responseDTO.setTotalFare(busBooking.getTotalFare());
//        responseDTO.setBookingTime(busBooking.getBookingTime());
//        responseDTO.setCancelled(busBooking.isCancelled());
//        return responseDTO;
//    }
//}
