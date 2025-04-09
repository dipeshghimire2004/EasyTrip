package org.easytrip.easytripbackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.easytrip.easytripbackend.dto.BookingPriceResponseDTO;
import org.easytrip.easytripbackend.dto.BookingRequestDTO;
import org.easytrip.easytripbackend.dto.BookingResponseDTO;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking API", description = "Endpoints for booking guesthouses")
public class BookingController {
    @Autowired
    private BookingService bookingService;

//    @GetMapping("/calculate")
//    @Operation(summary = "Calculate booking price", description = "Calculates the total price and displays payment options.")
//    public ResponseEntity<BookingPriceResponseDTO> calculateBookingPrice(
//            @Parameter(description = "ID of the guesthouse") @RequestParam Long guesthouseId,
//            @Parameter(description = "Check-in date (yyyy-MM-dd)") @RequestParam String checkInDate,
//            @Parameter(description = "Check-out date (yyyy-MM-dd)") @RequestParam String checkOutDate) {
//
//        BookingPriceResponseDTO response =bookingService.calculateBookingPrice(guesthouseId, checkInDate, checkOutDate);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Book a guesthouse", description = "Books a guesthouse for a traveler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or guesthouse not approved"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires CLIENT role")
    })
    public ResponseEntity<BookingResponseDTO> bookGuesthouse(@RequestBody BookingRequestDTO requestDTO) {
        BookingResponseDTO booking=bookingService.bookGuesthouse(requestDTO);
        return ResponseEntity.ok(booking);
    }
}
