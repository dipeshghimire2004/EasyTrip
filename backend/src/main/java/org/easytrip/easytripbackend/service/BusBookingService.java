package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BusBookingRequestDTO;
import org.easytrip.easytripbackend.dto.BusBookingResponseDTO;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Bus;
import org.easytrip.easytripbackend.model.BusBooking;
import org.easytrip.easytripbackend.model.PaymentMethod;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BusBookingRepository;
import org.easytrip.easytripbackend.repository.BusRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private BusNotificationService busNotificationService;

    public BusBookingResponseDTO bookBus(BusBookingRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Booking bus ID: {} by email: {}", request.getBusId(), email);
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getRole().equals(Role.CLIENT)) {
            logger.error("User with email {} is not a CLIENT", email);
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
        booking.setPaymentMethod(PaymentMethod.CASH_ON_ARRIVAL);

        BusBooking savedBooking = busBookingRepository.save(booking);

        // Send notifications
        double totalFare = bus.getFarePerSeat() * request.getSeatsBooked();
        busNotificationService.sendBookingNotification(
                client.getEmail(),
                bus.getOperator().getEmail(),
                bus.getName(),
                client.getName(),
                bus.getSource(),
                bus.getDestination(),
                bus.getDepartureTime().toString(),
                bus.getArrivalTime().toString(),
                request.getSeatsBooked(),
                totalFare
        );

        return mapToResponse(savedBooking);
    }

    public BusBookingResponseDTO cancelBooking(Long bookingId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Cancelling booking ID: {} by email: {}", bookingId, email);
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getRole().equals(Role.CLIENT)) {
            logger.error("User with email {} is not a CLIENT", email);
            throw new RuntimeException("User is not a client");
        }

        BusBooking booking = busBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getClient().getId().equals(client.getId())) {
            logger.error("User with email {} is not authorized to cancel booking ID: {}", email, bookingId);
            throw new RuntimeException("Unauthorized to cancel this booking");
        }

        booking.setCancelled(true);
        BusBooking updatedBooking = busBookingRepository.save(booking);

        // Send notifications
        Bus bus = booking.getBus();
        busNotificationService.sendCancellationNotification(
                client.getEmail(),
                bus.getOperator().getEmail(),
                bus.getName(),
                client.getName(),
                bus.getSource(),
                bus.getDestination(),
                bus.getDepartureTime().toString(),
                bus.getArrivalTime().toString()
        );

        return mapToResponse(updatedBooking);
    }

    public List<BusBookingResponseDTO> getClientBookings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Fetching bookings for email: {}", email);
        User client = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getRole().equals(Role.CLIENT)) {
            logger.error("User with email {} is not a CLIENT", email);
            throw new RuntimeException("User is not a client");
        }

        return busBookingRepository.findByClientIdAndIsCancelledFalse(client.getId())
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
        response.setPaymentMethod(booking.getPaymentMethod());
        return response;
    }
}