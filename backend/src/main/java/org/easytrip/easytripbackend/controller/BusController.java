package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.easytrip.easytripbackend.dto.BusBookingRequestDTO;
import org.easytrip.easytripbackend.dto.BusBookingResponseDTO;
import org.easytrip.easytripbackend.dto.BusRequestDTO;
import org.easytrip.easytripbackend.dto.BusResponseDTO;
import org.easytrip.easytripbackend.dto.BusScheduleRequestDTO;
import org.easytrip.easytripbackend.dto.BusScheduleResponseDTO;
import org.easytrip.easytripbackend.dto.BusScheduleSearchRequestDTO;
import org.easytrip.easytripbackend.dto.BusScheduleSearchResponseDTO;
import org.easytrip.easytripbackend.model.Bus;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
@Tag(name = "Bus Management", description = "Operations related to bus management, scheduling, and booking")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusScheduleService busScheduleService;

//    @Autowired
//    private BusBookingService busBookingService;

    @Operation(summary = "Register a new bus", description = "Allows bus operators to register a new bus in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus registered successfully",
                    content = @Content(schema = @Schema(implementation = BusResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - only bus operators can perform this action")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    public ResponseEntity<BusResponseDTO> registerBus(@ModelAttribute BusRequestDTO requestDTO) {
        return ResponseEntity.ok(busService.registerBus(requestDTO));
    }

    @Operation(summary = "Update bus information", description = "Allows bus operators or admins to update bus details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus updated successfully",
                    content = @Content(schema = @Schema(implementation = BusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized access")
    })
    @PutMapping("/{busId}")
    @PreAuthorize("hasRole('BUS_OPERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BusResponseDTO> updateBus(
            @Parameter(description = "ID of the bus to be updated") @PathVariable Long busId,
            @RequestBody BusRequestDTO requestDTO) {
        return ResponseEntity.ok(busService.updateBus(requestDTO, busId));
    }

    @Operation(summary = "Delete a bus", description = "Allows bus operators or admins to delete a bus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized access")
    })
    @DeleteMapping("/{busId}")
    @PreAuthorize("hasRole('BUS_OPERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBus(
            @Parameter(description = "ID of the bus to be deleted") @PathVariable Long busId) {
        busService.deleteBus(busId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Approve a bus", description = "Allows admins to approve a bus registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus approved successfully",
                    content = @Content(schema = @Schema(implementation = BusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only admins can perform this action")
    })
    @PutMapping("/approve/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusResponseDTO> approveBus(
            @Parameter(description = "ID of the bus to be approved") @PathVariable Long busId) {
        return ResponseEntity.ok(busService.approveBus(busId));
    }

    @Operation(summary = "Reject a bus", description = "Allows admins to reject a bus registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus rejected successfully",
                    content = @Content(schema = @Schema(implementation = BusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only admins can perform this action")
    })
    @PutMapping("/reject/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BusResponseDTO> rejectBus(
            @Parameter(description = "ID of the bus to be rejected") @PathVariable Long busId) {
        return ResponseEntity.ok(busService.rejectBus(busId));
    }

    @Operation(summary = "Get all buses", description = "Retrieves a list of all buses in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of buses",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<BusResponseDTO>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @Operation(summary = "Get bus by ID", description = "Retrieves bus details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bus details",
                    content = @Content(schema = @Schema(implementation = BusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bus not found")
    })
    @GetMapping("/{busId}")
    public ResponseEntity<BusResponseDTO> getBus(
            @Parameter(description = "ID of the bus to be retrieved") @PathVariable Long busId) {
        return ResponseEntity.ok(busService.getBus(busId));
    }

    @Operation(summary = "Add bus schedule", description = "Allows bus operators to add a new schedule for a bus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule added successfully",
                    content = @Content(schema = @Schema(implementation = BusScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - only bus operators can perform this action")
    })
    @PostMapping("/schedule")
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    public ResponseEntity<BusScheduleResponseDTO> addSchedule(@RequestBody BusScheduleRequestDTO requestDTO) {
        return ResponseEntity.ok(busScheduleService.addBusSchedule(requestDTO));
    }

    @Operation(summary = "Update bus schedule", description = "Allows bus operators to update an existing bus schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule updated successfully",
                    content = @Content(schema = @Schema(implementation = BusScheduleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only bus operators can perform this action")
    })
    @PutMapping("/schedule/{scheduleId}")
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    public ResponseEntity<BusScheduleResponseDTO> updateSchedule(
            @Parameter(description = "ID of the schedule to be updated") @PathVariable Long scheduleId,
            @RequestBody BusScheduleRequestDTO requestDTO) {
        return ResponseEntity.ok(busScheduleService.updateBusSchedule(requestDTO, scheduleId));
    }

    @Operation(summary = "Delete bus schedule", description = "Allows bus operators to delete a bus schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only bus operators can perform this action")
    })
    @DeleteMapping("/schedule/{scheduleId}")
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    public ResponseEntity<Void> deleteSchedule(
            @Parameter(description = "ID of the schedule to be deleted") @PathVariable Long scheduleId) {
        busScheduleService.deleteBusSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Search bus schedules", description = "Allows clients to search for available bus schedules based on criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved matching schedules",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusScheduleSearchResponseDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden - only clients can perform this action")
    })
    @PostMapping("/schedule/search")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BusScheduleSearchResponseDTO>> searchSchedules(@RequestBody BusScheduleSearchRequestDTO requestDTO) {
        return ResponseEntity.ok(busScheduleService.searchBusSchedules(requestDTO));
    }

//    @Operation(summary = "Book a bus", description = "Allows clients to book seats on a bus schedule")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Booking successful",
//                    content = @Content(schema = @Schema(implementation = BusBookingResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request - invalid booking details"),
//            @ApiResponse(responseCode = "403", description = "Forbidden - only clients can perform this action")
//    })
//    @PostMapping("/book")
//    @PreAuthorize("hasRole('CLIENT')")
//    public ResponseEntity<BusBookingResponseDTO> bookBus(@RequestBody BusBookingRequestDTO requestDTO) {
//        return ResponseEntity.ok(busBookingService.bookBus(requestDTO));
//    }
//
//    @Operation(summary = "Cancel booking", description = "Allows clients to cancel their bus booking")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
//            @ApiResponse(responseCode = "404", description = "Booking not found"),
//            @ApiResponse(responseCode = "403", description = "Forbidden - only the booking owner can perform this action")
//    })
//    @PutMapping("/cancel/{bookingId}")
//    @PreAuthorize("hasRole('CLIENT')")
//    public ResponseEntity<Void> cancelBooking(
//            @Parameter(description = "ID of the booking to be cancelled") @PathVariable Long bookingId) {
//        busBookingService.cancelBooking(bookingId);
//        return ResponseEntity.ok().build();
//    }
}