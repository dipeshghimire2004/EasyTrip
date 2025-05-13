package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.RoomRequestDTO;
import org.easytrip.easytripbackend.dto.RoomResponseDTO;
import org.easytrip.easytripbackend.model.Booking;
import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.Room;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BookingRepository;
import org.easytrip.easytripbackend.repository.GuesthouseRepository;
import org.easytrip.easytripbackend.repository.RoomRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuesthouseRepository guesthouseRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RoomResponseDTO createRoom(Long guesthouseId, RoomRequestDTO request){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Creating a new room to guesthouse ID:{} by user:{}", guesthouseId, email);
        User user =userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
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

        if (request.getPricePerNight() <= 0) {
            throw new IllegalArgumentException("Price per night must be positive");
        }
        if (request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        Room room = new Room();
        room.setGuesthouseId(guesthouseId);
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setAvailable(true);
        room.setCapacity(request.getCapacity());

        Room savedRoom = roomRepository.save(room);
        logger.info("Room added with ID {} to guesthouse ID {}",room.getId(), guesthouseId);

        return mapToResponseDTO(savedRoom);
    }


    @Transactional
    public RoomResponseDTO updateRoom(Long guesthouseId,Long roomId, RoomRequestDTO request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Updating room ID:{} to guesthouse ID:{} by user:{}",roomId, guesthouseId, email);

        User user = authService.findByEmail(email);
        if(!user.getRole().contains(Role.HOTEL_MANAGER)){
            logger.warn("User {} does not have the role HOTEL_MANAGER", email);
            throw new IllegalArgumentException("User " + email + " does not have the role HOTEL_MANAGER");
        }

        Guesthouse guesthouse= guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new IllegalArgumentException("Guesthouse " + guesthouseId + " not found"));

        if((!guesthouse.getOwner().getId().equals(user.getId()))){
            logger.warn("User {} is not owner of guesthouse {}",email, guesthouseId);
            throw new IllegalArgumentException("User " + email + " is not owner of guesthouse");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room " + roomId + " not found"));

        if(!room.getGuesthouseId().equals(guesthouseId) ){
            logger.warn("Room {} does not belong to guesthouse ID {}",roomId, guesthouseId);
            throw new IllegalArgumentException("Room " + roomId + " does not belong to guesthouse");
        }

        if(!room.getRoomNumber().equals(request.getRoomNumber()) && roomRepository.existsByGuesthouseIdAndRoomNumber(guesthouseId, request.getRoomNumber())){
            logger.warn("Room {} already exists for guesthouse ID {}",roomId, guesthouseId);
            throw new IllegalArgumentException("Room " + request.getRoomNumber() + " already exists for guesthouse");
        }

        if(request.getPricePerNight() <= 0){
            throw new IllegalArgumentException("Price per night must be positive");
        }
        if(request.getCapacity() <= 0){
            throw new IllegalArgumentException("Capacity must be positive");
        }

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setAvailable(request.isAvailable());
        room.setCapacity(request.getCapacity());
        Room updatedRoom = roomRepository.save(room);
        logger.info("Room updated with ID {} to guesthouse ID {}",room.getId(), guesthouseId);

        return mapToResponseDTO(updatedRoom);
    }

    public List<RoomResponseDTO> getRoomsByGuesthouseId(Long guesthouseId, LocalDate checkInDate, LocalDate checkOutDate){
        logger.info("Fetching rooms for guesthouse ID {} with checkIn :{} and checkOut :{}",guesthouseId, checkInDate, checkOutDate);

        List<Room> rooms= roomRepository.findByGuesthouseId(guesthouseId);

        //Filter rooms based on availability  for the given data range
        return rooms.stream()
                .filter(room -> isRoomAvailable(room.getId(), checkInDate, checkOutDate))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutdate){
        if(checkInDate ==null || checkOutdate == null || checkInDate.isAfter(checkOutdate)){
            logger.warn("Invalid date range: checkInDate {} and checkOutDate {}", checkInDate, checkOutdate);
            return false;
        }

        List<Booking> overlappingBooking= bookingRepository.findOverlappingBookings(roomId, checkInDate, checkOutdate);
        boolean isAvailable = overlappingBooking.isEmpty();
        return isAvailable;
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
