package org.easytrip.easytripbackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.easytrip.easytripbackend.dto.BookingRequestDTO;
import org.easytrip.easytripbackend.dto.BookingResponseDTO;
import org.easytrip.easytripbackend.dto.BookingUpdateRequestDTO;
import org.easytrip.easytripbackend.service.GuesthouseBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ConcurrentModificationException;


@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking API", description = "Endpoints for booking guesthouses")
public class GuesthouseBookingController {
    @Autowired
    private GuesthouseBookingService guesthouseBookingService;


    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Book a guesthouse", description = "Books a guesthouse for a traveler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or guesthouse not approved"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires CLIENT role")
    })
    public ResponseEntity<BookingResponseDTO> bookGuesthouse(@RequestBody BookingRequestDTO requestDTO) {
        BookingResponseDTO booking= guesthouseBookingService.bookGuesthouse(requestDTO);
        return ResponseEntity.ok(booking);
    }


    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Cancel guesthouse booking", description = "Cancel a guesthouse booked by a traveler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking Cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or guesthouse not approved"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires CLIENT role")
    })
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long bookingId) {
        BookingResponseDTO response= guesthouseBookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Cancel guesthouse booking", description = "Cancel a guesthouse booked by a traveler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking Cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or guesthouse not approved"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires CLIENT role")
    })
    public ResponseEntity<BookingResponseDTO> modifyBooking(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingUpdateRequestDTO request) {
        BookingResponseDTO response = guesthouseBookingService.modifyBooking(bookingId, request);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConcurrentModificationException.class)
    public ResponseEntity<String> handleConcurrentModification(ConcurrentModificationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Operation failed due to concurrent modification. Please try again.");
    }
}
