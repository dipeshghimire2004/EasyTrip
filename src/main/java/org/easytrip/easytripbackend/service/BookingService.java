package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BookingPriceResponseDTO;
import org.easytrip.easytripbackend.dto.BookingRequestDTO;
import org.easytrip.easytripbackend.dto.BookingResponseDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.model.Booking;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BookingRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private GuesthouseService guesthouseService;

    @Autowired
    private UserRepository userRepository;


    public BookingPriceResponseDTO calculateBookingPrice(Long guesthouseId, String checkInDate, String checkOutDate) {
        Guesthouse guesthouse = guesthouseService.getGuesthouseEntityById(guesthouseId);
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if(nights < 0) {
            throw new RuntimeException("Nights must be greater than zero");
        }
        BookingPriceResponseDTO bookingPriceResponseDTO = new BookingPriceResponseDTO();
        bookingPriceResponseDTO.setGuesthouseId(guesthouseId);
        bookingPriceResponseDTO.setCheckInDate(checkIn.toString());
        bookingPriceResponseDTO.setCheckOutDate(checkOut.toString());
        bookingPriceResponseDTO.setTotalPrice(nights * guesthouse.getPricePerNight());

        return bookingPriceResponseDTO;
    }

    public BookingResponseDTO bookGuesthouse(BookingRequestDTO request) {
        User traveler = authService.findByUserId(request.getUserId());
        if(!traveler.getRole().contains(Role.CLIENT)) {
            throw new RuntimeException("You are not the client/traveller of this traveler");
        }

//        Guesthouse guesthouse = guesthouse.service
        Guesthouse guesthouse= guesthouseService.getGuesthouseEntityById(request.getGuesthouseId());
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

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponseDto(savedBooking);// need to be improved
    }

    public Booking findById(long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));
    }

    private BookingResponseDTO mapToResponseDto(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(booking.getId());
        bookingResponseDTO.setUserId(booking.getTraveler().getId());
        bookingResponseDTO.setGuesthouseId(booking.getGuesthouse().getId());
        bookingResponseDTO.setCheckInDate(booking.getCheckInDate());
        bookingResponseDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingResponseDTO.setTotalPrice(booking.getTotalPrice());
        return bookingResponseDTO;
    }
}
