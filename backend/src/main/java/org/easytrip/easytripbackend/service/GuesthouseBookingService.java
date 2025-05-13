package org.easytrip.easytripbackend.service;

import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import org.easytrip.easytripbackend.dto.BookingRequestDTO;
import org.easytrip.easytripbackend.dto.BookingResponseDTO;
import org.easytrip.easytripbackend.dto.BookingUpdateRequestDTO;
import org.easytrip.easytripbackend.model.Booking;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.Room;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BookingRepository;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.repository.RoomRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GuesthouseBookingService {
    private static Logger logger = LoggerFactory.getLogger(GuesthouseBookingService.class);
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private GuesthouseService guesthouseService;

    @Autowired
    private GuesthouseRepository guesthouseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuesthouseNotificationService guesthouseNotificationService;

    private static final int MAX_RETRIES = 3;

    @Transactional
    public BookingResponseDTO bookGuesthouse(BookingRequestDTO request) {
        // Get the authenticated user (traveler)
        LocalDate now = LocalDate.now();
        if(request.getCheckInDate().isBefore(now)){
            throw new IllegalArgumentException("CheckInDate cannot be in the past");
        }
        if(request.getCheckOutDate().isBefore(request.getCheckInDate())){
            throw new IllegalArgumentException("CheckOutDate must be after checkInDate");
        }

        //Get authenticated user
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User traveler= authService.findByEmail(email);
        if(!traveler.getRole().contains(Role.CLIENT)) {
            throw new RuntimeException("You are not the client/traveller of this traveler");
        }

        //Get guesthouse
        Guesthouse guesthouse = guesthouseRepository.findById(request.getGuesthouseId())
                .orElseThrow(()-> new IllegalArgumentException("Guesthouse not found"));

        // Get room
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(()-> new IllegalArgumentException("Room not found"));

        if(!room.getGuesthouseId().equals(guesthouse.getId())){
            throw new IllegalArgumentException("Room does not belong to specified guesthouse");
        }

        if(!room.isAvailable()){
            throw new IllegalArgumentException("Room is not available");
        }

//        Guesthouse guesthouse= guesthouseService.getGuesthouseEntityById(request.getGuesthouseId());
        LocalDate checkInDate = request.getCheckInDate();
        LocalDate checkOutDate = request.getCheckOutDate();

        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        if(nights <= 0) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        Booking booking = new Booking();
        booking.setTraveler(traveler);
        booking.setGuesthouse(guesthouse);
        booking.setRoom(room);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalPrice(nights * room.getPricePerNight());
        booking.setStatus("CONFIRMED");
        booking.setPaymentOption("Cash on Arrival");

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking saved successfully with id{}", savedBooking.getId());

        //update availability
        room.setAvailable(false);
        roomRepository.save(room);

        //notify the gueshouseowner
        User owner = authService.findByUserId(guesthouse.getOwner().getId());
        if(owner != null && owner.getRole().contains(Role.HOTEL_MANAGER)) {
            guesthouseNotificationService.sendBookingNotification(
                    owner.getEmail(),
                    guesthouse.getName(),
                    traveler.getName(),
                    request.getCheckInDate().toString(),
                    request.getCheckOutDate().toString(),
                    booking.getTotalPrice()
            );
        }
        else{
            logger.warn("no valid owner found for gueshouse ID : {}", guesthouse.getId());
        }
        return mapToResponseDto(savedBooking);
    }



    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRoomAvailability() {
        LocalDate today = LocalDate.now();
        logger.info("Running room availability update task on {}", today);

        List<Booking> expiredBookings = bookingRepository.findByStatusAndCheckOutDateBefore("CONFIRMED", today);
        if (expiredBookings.isEmpty()) {
            logger.info("No expired bookings found");
            return;
        }

        for (Booking booking : expiredBookings) {
            int attempt = 0;
            while (attempt < MAX_RETRIES) {
                try {
                    Room room = booking.getRoom();
                    if (room != null && !room.isAvailable()) {
                        room.setAvailable(true);
                        roomRepository.save(room);
                        booking.setStatus("COMPLETED");
                        bookingRepository.save(booking);
                        logger.info("Set room {} available and marked booking {} as COMPLETED", room.getId(), booking.getId());
                    }
                    break; // Success, move to next booking
                } catch (OptimisticLockException e) {
                    attempt++;
                    logger.warn("Concurrent modification detected for booking id {}. Retry attempt {}/{}", booking.getId(), attempt, MAX_RETRIES);
                    if (attempt == MAX_RETRIES) {
                        logger.error("Failed to update booking id {} after {} retries", booking.getId(), MAX_RETRIES);
                        break; // Move to next booking
                    }
                    try {
                        Thread.sleep(100 * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        logger.error("Retry interrupted", ie);
                        break;
                    }
                }
            }
        }
    }

    public BookingResponseDTO getAllGuesthouseBookings() {
        logger.info("Fetching all bookings");
        List<Booking> bookings = bookingRepository.findAll();
        if(bookings.isEmpty()){
            logger.info("No bookings found");
            return null;
        }
        return bookings.stream().map(this::mapToResponseDto).collect(Collectors.toList()).get(0);
    }





    @Transactional
    public void cancelBooking(Long bookingId) {
        try {
            // Fetch booking with pessimistic lock
            Booking booking = bookingRepository.findById(bookingId, LockModeType.PESSIMISTIC_WRITE)
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            logger.info("Attempting to cancel booking id {} with status {} and room id {}",
                    bookingId, booking.getStatus(), booking.getRoom().getId());

            if (!booking.getStatus().equals("CONFIRMED")) {
                throw new IllegalStateException("Only CONFIRMED bookings can be cancelled");
            }

            // Fetch room with pessimistic lock
            Room room = roomRepository.findById(booking.getRoom().getId(), LockModeType.PESSIMISTIC_WRITE)
                    .orElseThrow(() -> new IllegalArgumentException("Room not found"));
            logger.info("Room id {} availability before update: {}", room.getId(), room.isAvailable());

            // Update entities
            room.setAvailable(true);
            booking.setStatus("CANCELLED");

            logger.info("Cancelled booking with id {}, room availability set to true", bookingId);
        } catch (Exception e) {
            logger.error("Failed to cancel booking id {}: {}", bookingId, e.getMessage(), e);
            throw new RuntimeException("Failed to cancel booking due to an unexpected error", e);
        }
    }

    public BookingResponseDTO modifyBooking(Long bookingId, BookingUpdateRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User traveler = authService.findByEmail(email);
        if (!traveler.getRole().contains(Role.CLIENT)) {
            throw new IllegalArgumentException("Only clients can modify bookings");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // Verify ownership
        if (!booking.getTraveler().getId().equals(traveler.getId())) {
            throw new IllegalArgumentException("You can only modify your own bookings");
        }

        if (booking.getStatus().equals("CANCELLED")) {
            throw new IllegalArgumentException("Cannot modify a cancelled booking");
        }

        LocalDate now = LocalDate.now();
        // Update fields if provided
        if (request.getCheckInDate() != null) {
            if (request.getCheckInDate().isBefore(now)) {
                throw new IllegalArgumentException("Check-in date cannot be in the past");
            }
            booking.setCheckInDate(request.getCheckInDate());
        }

        if (request.getCheckOutDate() != null) {
            if (request.getCheckOutDate().isBefore(booking.getCheckInDate())) {
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }
            booking.setCheckOutDate(request.getCheckOutDate());
        }

        // Recalculate totalPrice
//        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
//        if (nights <= 0) {
//            throw new IllegalArgumentException("Check-out date must be after check-in date");
//        }
//        booking.setTotalPrice(nights * booking.getGuesthouse().getPricePerNight());


        booking.setStatus("MODIFIED"); // Optional, for tracking

        Booking updatedBooking = bookingRepository.save(booking);
        logger.info("Booking modified with ID: {}", bookingId);
        return mapToResponseDto(updatedBooking);
    }

    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }



    private BookingResponseDTO mapToResponseDto(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingId(booking.getId());
       bookingResponseDTO.setGuesthouseId(booking.getGuesthouse().getId());
       bookingResponseDTO.setRoomId(booking.getRoom().getId());
        bookingResponseDTO.setGuesthouseName(booking.getGuesthouse().getName());
        bookingResponseDTO.setCheckInDate(booking.getCheckInDate());
        bookingResponseDTO.setCheckOutDate(booking.getCheckOutDate());
//        bookingResponseDTO.setTotalPrice(booking.getTotalPrice());
        bookingResponseDTO.setPaymentOption(booking.getPaymentOption());
        bookingResponseDTO.setStatus(booking.getStatus());
        return bookingResponseDTO;
    }
}
