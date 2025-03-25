package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.easytrip.easytripbackend.dto.GuesthouseRequestDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.service.GuesthouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/guesthouses")
@Tag(name = "Guesthouse", description = "API for guesthouse management")
public class GuesthouseController {

    @Autowired
    private GuesthouseService guesthouseService;

    @PostMapping
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @Operation(summary="Register a new guesthouse", description="Allows a HOTEL_MANAGER to register their guesthouse")
    @ApiResponse(responseCode = "200", description = "Guesthouse registered successfully")
    public ResponseEntity<GuesthouseResponseDTO> register(@RequestBody GuesthouseRequestDTO request) {
        GuesthouseResponseDTO response= guesthouseService.registerGuesthouse(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve a guesthouse", description = "Allows an ADMIN to approve a pending guesthouse")
    @ApiResponse(responseCode = "200", description = "Guesthouse approved successfully")
    public ResponseEntity<GuesthouseResponseDTO> approve(@PathVariable("id") long id) {
        GuesthouseResponseDTO response= guesthouseService.approveGuesthouse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(".{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GuesthouseResponseDTO> reject(@PathVariable Long id){
        GuesthouseResponseDTO response= guesthouseService.rejectGuesthouse(id);
        return ResponseEntity.ok(response);
    }
}
