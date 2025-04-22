package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.easytrip.easytripbackend.dto.RoomRequestDTO;
import org.easytrip.easytripbackend.dto.RoomResponseDTO;
import org.easytrip.easytripbackend.model.Room;
import org.easytrip.easytripbackend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guesthouses/{guesthouseId}/rooms")
@Tag(name = "Room API", description = "Endpoints for managing rooms in a guesthouse")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @Operation(summary="Book a new room", description="Allows a HOTEL_MANAGER to add their room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "room booked successfully" , content = @Content(schema =@Schema(implementation = RoomResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires HOTEL_MANAGER role"),
            @ApiResponse(responseCode = "404", description = "Guesthouse or room not found"),
            @ApiResponse(responseCode = "400", description = "Invalid room details")
    })
    public ResponseEntity<RoomResponseDTO> createRoom(@PathVariable long guesthouseId,@Valid @RequestBody RoomRequestDTO request) {
        RoomResponseDTO response=roomService.createRoom(guesthouseId, request);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @Operation(summary="Update a guesthouse", description="Allows a HOTEL_MANAGER to update their room")
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description="room updated successfully", content = @Content(schema =@Schema(implementation = RoomResponseDTO.class))),
            @ApiResponse(responseCode="403", description="Forbidden - Requires HOTEL_MANAGER role"),
            @ApiResponse(responseCode="404", description="Room not found"),
            @ApiResponse(responseCode="400", description="Invalid room details")
    })
    public ResponseEntity<RoomResponseDTO> updateRoom(
            @Parameter(description="Id of the guesthouse", required=true)
            @PathVariable long guesthouseId,
            @Parameter(description="Id of the room", required=true)
            @PathVariable long roomId,
            @RequestBody RoomRequestDTO request) {
        RoomResponseDTO response= roomService.updateRoom(guesthouseId, roomId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary="get rooms by guesthouse", description="Retrieve a lists of room for a specific guesthouse, optionally filtered by availability")
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "List of rooms retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Room.class))
            ),
            @ApiResponse(responseCode = "404", description = "Guesthouse not found")
    })
    public ResponseEntity<List<RoomResponseDTO>> getRooms(
            @Parameter(description = "ID of the guesthouse", required = true)
            @PathVariable Long guesthouseId,
            @Parameter(description = "Check-in date (yyyy-MM-dd)", required = false)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @Parameter(description = "Check-out date (yyyy-MM-dd)", required = false)
            @RequestParam(required=false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate  checkOutDate
            ){
        List<RoomResponseDTO> response = roomService.getRoomsByGuesthouseId(guesthouseId,checkInDate, checkOutDate);
        return ResponseEntity.ok(response);
    }
}
