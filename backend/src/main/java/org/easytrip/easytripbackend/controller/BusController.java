package org.easytrip.easytripbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.easytrip.easytripbackend.dto.BusBookingRequestDTO;
import org.easytrip.easytripbackend.dto.BusBookingResponseDTO;
import org.easytrip.easytripbackend.dto.BusRequestDTO;
import org.easytrip.easytripbackend.dto.BusResponseDTO;
import org.easytrip.easytripbackend.dto.BusScheduleRequestDTO;
import org.easytrip.easytripbackend.dto.BusScheduleResponseDTO;
import org.easytrip.easytripbackend.dto.BusScheduleSearchRequestDTO;
import org.easytrip.easytripbackend.dto.BusScheduleSearchResponseDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.model.Bus;
import org.easytrip.easytripbackend.service.BusBookingService;
import org.easytrip.easytripbackend.service.BusScheduleService;
import org.easytrip.easytripbackend.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


import java.util.List;



@RestController
@RequestMapping("/api/buses")
@Tag(name = "Bus Management", description = "APIs for managing buses and bookings")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusBookingService busBookingService;

    @Operation(summary = "Register a new bus", description = "Allows a BUS_OPERATOR to register a bus with a verification document")
    @ApiResponse(responseCode = "200", description = "Bus registered successfully", content = @Content(schema = @Schema(implementation = BusResponseDTO.class)))
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BusResponseDTO> registerBus(@Valid @ModelAttribute BusRequestDTO request) {
        return ResponseEntity.ok(busService.registerBus(request));
    }

    @Operation(summary = "Update a bus", description = "Allows a BUS_OPERATOR or ADMIN to update bus details")
    @ApiResponse(responseCode = "200", description = "Bus updated successfully", content = @Content(schema = @Schema(implementation = BusResponseDTO.class)))
    @PutMapping(value = "/{busId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BusResponseDTO> updateBus(
            @Parameter(description = "ID of the bus to update") @PathVariable Long busId,
            @Valid @ModelAttribute BusRequestDTO request,
            @Parameter(description = "ID of the user (BUS_OPERATOR or ADMIN)") @RequestParam Long userId) {
        return ResponseEntity.ok(busService.updateBus(busId, request, userId));
    }


    @Operation(summary = "Get Pending buses", description = "Allows a  ADMIN to view all the pending buses")
    @GetMapping
    public ResponseEntity<List<BusResponseDTO>> getPendingBuses(){
        return ResponseEntity.ok(busService.getPendingBuses());
    }

    @Operation(summary = "Get Approved buses", description = "Allows a  ADMIN to view all the Approved buses")
    @GetMapping
    public ResponseEntity<List<BusResponseDTO>> getApprovedBuses(){
        return ResponseEntity.ok(busService.getApprovedBuses());
    }


    @Operation(summary = "Delete a bus", description = "Allows a BUS_OPERATOR or ADMIN to delete a bus")
    @ApiResponse(responseCode = "200", description = "Bus deleted successfully")
    @DeleteMapping("/{busId}")
    public ResponseEntity<Void> deleteBus(
            @Parameter(description = "ID of the bus to delete") @PathVariable Long busId,
            @Parameter(description = "ID of the user (BUS_OPERATOR or ADMIN)") @RequestParam Long userId) {
        busService.deleteBus(busId, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Approve a bus", description = "Allows an ADMIN to approve a bus")
    @ApiResponse(responseCode = "200", description = "Bus approved successfully", content = @Content(schema = @Schema(implementation = BusResponseDTO.class)))
    @PutMapping("/{busId}/approve")
    public ResponseEntity<BusResponseDTO> approveBus(
            @Parameter(description = "ID of the bus to approve") @PathVariable Long busId,
            @Parameter(description = "ID of the admin") @RequestParam Long adminId) {
        return ResponseEntity.ok(busService.approveBus(busId, adminId));
    }

    @Operation(summary = "Deny a bus", description = "Allows an ADMIN to deny a bus")
    @ApiResponse(responseCode = "200", description = "Bus denied successfully", content = @Content(schema = @Schema(implementation = BusResponseDTO.class)))
    @PutMapping("/{busId}/deny")
    public ResponseEntity<BusResponseDTO> denyBus(
            @Parameter(description = "ID of the bus to deny") @PathVariable Long busId,
            @Parameter(description = "ID of the admin") @RequestParam Long adminId) {
        return ResponseEntity.ok(busService.denyBus(busId, adminId));
    }

    @Operation(summary = "Search buses", description = "Allows anyone to search for approved buses by source and destination")
    @ApiResponse(responseCode = "200", description = "List of approved buses", content = @Content(schema = @Schema(implementation = BusResponseDTO.class)))
    @GetMapping("/search")
    public ResponseEntity<List<BusResponseDTO>> searchBuses(
            @Parameter(description = "Source location (optional)") @RequestParam(required = false) String source,
            @Parameter(description = "Destination location (optional)") @RequestParam(required = false) String destination) {
        return ResponseEntity.ok(busService.searchBuses(source, destination));
    }

    @Operation(summary = "Book a bus", description = "Allows a CLIENT to book seats on a bus")
    @ApiResponse(responseCode = "200", description = "Booking created successfully", content = @Content(schema = @Schema(implementation = BusBookingResponseDTO.class)))
    @PostMapping("/book")
    public ResponseEntity<BusBookingResponseDTO> bookBus(
            @Valid @RequestBody BusBookingRequestDTO request,
            @Parameter(description = "ID of the client") @RequestParam Long clientId) {
        return ResponseEntity.ok(busBookingService.bookBus(request, clientId));
    }

    @Operation(summary = "Cancel a booking", description = "Allows a CLIENT to cancel their booking")
    @ApiResponse(responseCode = "200", description = "Booking cancelled successfully", content = @Content(schema = @Schema(implementation = BusBookingResponseDTO.class)))
    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<BusBookingResponseDTO> cancelBooking(
            @Parameter(description = "ID of the booking to cancel") @PathVariable Long bookingId,
            @Parameter(description = "ID of the client") @RequestParam Long clientId) {
        return ResponseEntity.ok(busBookingService.cancelBooking(bookingId, clientId));
    }

    @Operation(summary = "View client bookings", description = "Allows a CLIENT to view their active bookings")
    @ApiResponse(responseCode = "200", description = "List of client bookings", content = @Content(schema = @Schema(implementation = BusBookingResponseDTO.class)))
    @GetMapping("/bookings")
    public ResponseEntity<List<BusBookingResponseDTO>> getClientBookings(
            @Parameter(description = "ID of the client") @RequestParam Long clientId) {
        return ResponseEntity.ok(busBookingService.getClientBookings(clientId));
    }
}
