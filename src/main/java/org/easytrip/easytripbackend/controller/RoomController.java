package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.easytrip.easytripbackend.dto.RoomRequestDTO;
import org.easytrip.easytripbackend.dto.RoomResponseDTO;
import org.easytrip.easytripbackend.model.Room;
import org.easytrip.easytripbackend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guesthouses/{guesthouseId}/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @Operation(summary="Book a new room", description="Allows a HOTEL_MANAGER to add their room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "room booked successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires HOTEL_MANAGER role")
    })
    public ResponseEntity<RoomResponseDTO> createRoom(@PathVariable long guesthouseId,@Valid @RequestBody RoomRequestDTO reqquest) {
        RoomResponseDTO response=roomService.createRoom(guesthouseId, reqquest);
        return ResponseEntity.ok(response);
    }
}
