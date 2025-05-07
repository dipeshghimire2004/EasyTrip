package org.easytrip.easytripbackend.service;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.easytrip.easytripbackend.dto.BusBookingRequestDTO;
import org.easytrip.easytripbackend.dto.BusBookingResponseDTO;
import org.easytrip.easytripbackend.exception.UnauthorizedRoleException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.Booking;
import org.easytrip.easytripbackend.model.BusBooking;
import org.easytrip.easytripbackend.model.BusSchedule;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BusBookingRepository;
import org.easytrip.easytripbackend.repository.BusScheduleRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BusBookingService {
    private static final Logger logger = LoggerFactory.getLogger(BusBookingService.class);
    @Autowired
    private BusBookingRepository busBookingRepository;

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BusBookingResponseDTO bookBus(BusBookingRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Booking bus by {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User with email " + email + " not found"));
        if(!user.getRole().contains(Role.CLIENT)){
            logger.warn("User {} is a not client", email);
            throw new UserNotFoundException("User " + email + " is not a client");
        }

        BusSchedule schedule = busScheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(()-> new RuntimeException("Schedule not found"));
        if(schedule.getAvailableSeats() < requestDTO.getNumberOfSeats()){
            logger.warn("Not enough seats available for schedule {}", schedule.getId());
            throw new UserNotFoundException("Schedule " + schedule.getId() + " is not enough available");
        }
        BusBooking booking = new BusBooking();
        booking.setSchedule(schedule);
        booking.setClient(user);
        booking.setNumberOfSeats(schedule.getAvailableSeats());
        booking.setTotalFare(requestDTO.getNumberOfSeats() * schedule.getFare());
        booking.setBookingTime(LocalDateTime.now());
        booking.setCancelled(false);

        schedule.setAvailableSeats(schedule.getAvailableSeats() - requestDTO.getNumberOfSeats());
        busScheduleRepository.save(schedule);
        BusBooking savedBooking = busBookingRepository.save(booking);
        logger.info("Booked bus with booking id :{}  for schedule id: {}", savedBooking.getId(), schedule.getId());
        return mapToResponseDTO(savedBooking);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Cancelling booking by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
        if (!user.getRole().contains(Role.CLIENT)) {
            logger.warn("User with email: {} is not a client", email);
            throw new UnauthorizedRoleException("User with email: " + email + " is not a client");
        }

        BusBooking booking = busBookingRepository.findById(bookingId)
                .orElseThrow(() -> new UserNotFoundException("Booking with id: " + bookingId + " not found"));
        if (!booking.getClient().getId().equals(user.getId())) {
            logger.error("User with email: {} does not own booking id: {}", email, bookingId);
            throw new UnauthorizedRoleException("User does not own this booking");
        }
        if (booking.isCancelled()) {
            logger.warn("Booking with id: {} is already cancelled", bookingId);
            throw new IllegalStateException("Booking is already cancelled");
        }

        BusSchedule schedule = booking.getSchedule();
        schedule.setAvailableSeats(schedule.getAvailableSeats() + booking.getNumberOfSeats());
        busScheduleRepository.save(schedule);

        booking.setCancelled(true);
        busBookingRepository.save(booking);

        logger.info("Cancelled booking with id: {}", bookingId);
    }

    private BusBookingResponseDTO mapToResponseDTO(BusBooking busBooking) {
        BusBookingResponseDTO responseDTO = new BusBookingResponseDTO();
        responseDTO.setId(busBooking.getId());
        responseDTO.setScheduleId(busBooking.getSchedule().getId());
        responseDTO.setNumberOfSeats(busBooking.getNumberOfSeats());
        responseDTO.setTotalFare(busBooking.getTotalFare());
        responseDTO.setBookingTime(busBooking.getBookingTime());
        responseDTO.setCancelled(busBooking.isCancelled());
        return responseDTO;
    }
}
