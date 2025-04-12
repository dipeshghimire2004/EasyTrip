package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BookingPriceResponseDTO;
import org.easytrip.easytripbackend.dto.BookingRequestDTO;
import org.easytrip.easytripbackend.dto.BookingResponseDTO;
import org.easytrip.easytripbackend.dto.BookingUpdateRequestDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.model.Booking;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BookingRepository;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    private static Logger logger = LoggerFactory.getLogger(BookingService.class);
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
    private NotificationService notificationService;

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
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalPrice(nights * guesthouse.getPricePerNight());
        booking.setStatus("CONFIRMED");

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking saved successfully with id{}", savedBooking.getId());

        //notify the gueshouseowner
        User owner = authService.findByUserId(guesthouse.getOwner().getId());
        if(owner != null && owner.getRole().contains(Role.HOTEL_MANAGER)) {
            notificationService.sendBookingNotification(
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

    public BookingResponseDTO cancelBooking(Long bookingId) {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User traveler= authService.findByEmail(email);
        if(!traveler.getRole().contains(Role.CLIENT)) {
            throw new RuntimeException("You are not the client/traveller of this traveler");
        }
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        //verify ownership
        if(!booking.getTraveler().equals(traveler.getId())) {
            throw new IllegalArgumentException("You can only cancel your booking booking");
        }

        //check cancellation policy (e.g. before check-in date
        if(booking.getCheckInDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Cannot cancel booking less than 24 hours before check-in");
        }

        booking.setStatus("CANCELLED");
        Booking updateBooking = bookingRepository.save(booking);
        return mapToResponseDto(updateBooking);
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
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if (nights <= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        booking.setTotalPrice(nights * booking.getGuesthouse().getPricePerNight());
        booking.setStatus("MODIFIED"); // Optional, for tracking

        Booking updatedBooking = bookingRepository.save(booking);
        logger.info("Booking modified with ID: {}", bookingId);
        return mapToResponseDto(updatedBooking);
    }

    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public Booking findById(long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
    }

    private BookingResponseDTO mapToResponseDto(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setBookingId(booking.getId());
       bookingResponseDTO.setGuesthouseId(booking.getGuesthouse().getId());
        bookingResponseDTO.setGuesthouseName(booking.getGuesthouse().getName());
        bookingResponseDTO.setCheckInDate(booking.getCheckInDate());
        bookingResponseDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingResponseDTO.setTotalPrice(booking.getTotalPrice());
        return bookingResponseDTO;
    }
}
