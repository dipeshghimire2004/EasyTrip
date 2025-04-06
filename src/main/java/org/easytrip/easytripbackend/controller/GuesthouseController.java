package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.easytrip.easytripbackend.dto.GuesthouseRequestDTO;
import org.easytrip.easytripbackend.dto.GuesthouseResponseDTO;
import org.easytrip.easytripbackend.exception.GuesthouseNotFoundException;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.service.FileStorageService;
import org.easytrip.easytripbackend.service.GuesthouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/guesthouses")
@Tag(name = "Guesthouse API", description = "API for guesthouse management")
public class GuesthouseController {

    @Autowired
    private GuesthouseService guesthouseService;

    @Autowired
    private GuesthouseRepository guesthouseRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @Operation(summary="Register a new guesthouse", description="Allows a HOTEL_MANAGER to register their guesthouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guesthouse registered successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires HOTEL_MANAGER role")
    })
    public ResponseEntity<GuesthouseResponseDTO> register(@ModelAttribute GuesthouseRequestDTO request) {
        GuesthouseResponseDTO response= guesthouseService.registerGuesthouse(request);
        return ResponseEntity.ok(response);
    }

    // ADMIN fetches all pending guesthouses
    @GetMapping("/admin/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all pending guesthouses")
    @ApiResponse(responseCode = "200", description = "List of pending guesthouses")
    public ResponseEntity<List<GuesthouseResponseDTO>> getPendingGuesthouses() {
        List<GuesthouseResponseDTO> pending = guesthouseService.getPendingGuesthouses();
        return ResponseEntity.ok(pending);
    }

    // ADMIN fetches a specific guesthouse by ID
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get guesthouse by ID")
    @ApiResponse(responseCode = "200", description = "Guesthouse details")
    public ResponseEntity<GuesthouseResponseDTO> getGuesthouseById(@PathVariable Long id) {
        GuesthouseResponseDTO response = guesthouseService.getGuesthouseById(id);
        return ResponseEntity.ok(response);
    }

    //fetch all the list of guesthouses
    @GetMapping()
    @Operation(summary =" Get guesthouses ")
    @ApiResponse(responseCode = "200", description = "Guesthouses lists")
    public ResponseEntity<List<GuesthouseResponseDTO>> getAllGuesthouses() {
        List<GuesthouseResponseDTO> response = guesthouseService.getAllGuesthouses();
        return ResponseEntity.ok(response);
    }
//    @PostMapping("/{id}/approve")
//
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve a guesthouse", description = "Allows an ADMIN to approve a pending guesthouse")
    @ApiResponse(responseCode = "200", description = "Guesthouse approved successfully")
    public ResponseEntity<GuesthouseResponseDTO> approve(@PathVariable("id") long id) {
        GuesthouseResponseDTO response= guesthouseService.approveGuesthouse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(".{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GuesthouseResponseDTO> reject(@PathVariable("id") Long id){
        GuesthouseResponseDTO response= guesthouseService.rejectGuesthouse(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("search")
    @Operation(summary="Search guesthouses", description="Filter guesthouses by name or location")
    @ApiResponse(responseCode="200", description="List of matching guesthouses")
    public ResponseEntity<List<GuesthouseResponseDTO>> searchGuesthouses(@RequestParam(required=false) String name,
                                                              @RequestParam(required=false) String location) {
        List<GuesthouseResponseDTO> response = guesthouseService.searchGuesthouses(name, location);
        return ResponseEntity.ok(response);
    }

    // Fetch document (for ADMIN or HOTEL_MANAGER)
    @GetMapping("/guesthouses/documents/{guesthouseId}")
    @PreAuthorize("hasAnyRole('HOTEL_MANAGER', 'ADMIN')")
    @Operation(summary = "Fetch guesthouse document")
    @ApiResponse(responseCode = "200", description = "Document file")
    public ResponseEntity<FileSystemResource> getDocument(@PathVariable Long guesthouseId) {
        GuesthouseResponseDTO guesthouse = guesthouseService.getGuesthouseById(guesthouseId);
        FileSystemResource file = new FileSystemResource(guesthouse.getVerifiedDocument());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
