package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.RoomRequestDTO;
import org.easytrip.easytripbackend.dto.RoomResponseDTO;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.Room;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuesthouseRepository guesthouseRepository;

    private AuthService authService;

    @Transactional
    public RoomResponseDTO createRoom(Long guesthouseId, RoomRequestDTO request){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Creating a new room to guesthouse ID:{} by user:{}", guesthouseId, email);
        User user =authService.findByEmail(email);
        if(!user.getRole().contains(Role.HOTEL_MANAGER)){
            logger.warn("User {} does not have the role HOTEL_MANAGER", email);
            throw new IllegalArgumentException("User " + email + " does not have the role HOTEL_MANAGER");
        }

        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new IllegalArgumentException("Guesthouse " + guesthouseId + " not found"));

        if(!guesthouse.getOwner().getId().equals(user.getId())){
            logger.warn("User {} is not owner of guesthouse {}",email, guesthouseId);
            throw new IllegalArgumentException("You can only add rooms to your own guesthouse");
        }
        if(roomRepository.existsByGuesthouseIdAndRoomNumber(guesthouseId, request.getRoomNumber())){
            logger.warn("Room {} already exists for guesthouse ID {}",request.getRoomNumber(), guesthouseId);
            throw new IllegalArgumentException("Room " + request.getRoomNumber() + " already exists for guesthouse");
        }

        Room room = new Room();
        room.setGuesthouseId(guesthouseId);
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setAvailable(request.isAvailable());
        room.setCapacity(request.getCapacity());

        Room savedRoom = roomRepository.save(room);
        logger.info("Room added with ID {} to guesthouse ID {}",room.getId(), guesthouseId);

        return mapToResponseDTO(savedRoom);
    }

    private RoomResponseDTO mapToResponseDTO(Room room){
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
        roomResponseDTO.setId(room.getId());
        roomResponseDTO.setRoomNumber(room.getRoomNumber());
        roomResponseDTO.setRoomType(room.getRoomType());
        roomResponseDTO.setPricePerNight(room.getPricePerNight());
        roomResponseDTO.setAvailable(room.isAvailable());
        roomResponseDTO.setCapacity(room.getCapacity());
        return roomResponseDTO;
    }

}
