package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.dto.BusScheduleRequestDTO;
import org.easytrip.easytripbackend.dto.BusScheduleResponseDTO;
import org.easytrip.easytripbackend.dto.BusScheduleSearchRequestDTO;
import org.easytrip.easytripbackend.dto.BusScheduleSearchResponseDTO;
import org.easytrip.easytripbackend.exception.UnauthorizedRoleException;
import org.easytrip.easytripbackend.exception.UserNotFoundException;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Bus;
import org.easytrip.easytripbackend.model.BusSchedule;
import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.repository.BusRepository;
import org.easytrip.easytripbackend.repository.BusScheduleRepository;
import org.easytrip.easytripbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusScheduleService {
//    private static final Logger logger = LoggerFactory.getLogger(BusScheduleService.class);
//    @Autowired
//    private BusScheduleRepository busScheduleRepository;
//
//    @Autowired
//    private BusRepository busRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public BusScheduleResponseDTO addBusSchedule(BusScheduleRequestDTO busScheduleRequestDTO) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Adding bus Schedule by email: " + email);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(()-> new UserNotFoundException("User with email: " + email + " not found"));
//
//        Bus bus = busRepository.findById(busScheduleRequestDTO.getBusId())
//                .orElseThrow(()-> new UserNotFoundException("Bus with id: " + busScheduleRequestDTO.getBusId() + " not found"));
//
//        if(!bus.getOperator().getUser().getId().equals(user.getId())) {
//            logger.error("User with email: {} is not operator of  bus ID: {}", email, busScheduleRequestDTO.getBusId());
//            throw new UserNotFoundException("User with email: " + email + " not found");
//        }
//
//        if(!bus.getStatus().equals(ApprovalStatus.APPROVED)){
//            logger.error("User with email: {} is not approved", email);
//            throw new UserNotFoundException("User with email: " + email + " is not approved");
//        }
//
//        boolean isBusOperator = user.getRole().contains(Role.BUS_OPERATOR);
//        BusSchedule busSchedule = new BusSchedule();
//        busSchedule.setSource(busScheduleRequestDTO.getSource());
//        busSchedule.setDestination(busScheduleRequestDTO.getDestination());
//        busSchedule.setDepartureTime(busScheduleRequestDTO.getDepartureTime());
//        busSchedule.setArrivalTime(busScheduleRequestDTO.getArrivalTime());
//        busSchedule.setFare(busScheduleRequestDTO.getFare());
//        busSchedule.setAvailableSeats(busScheduleRequestDTO.getAvailableSeats());
//
//        BusSchedule savedBusSchedule = busScheduleRepository.save(busSchedule);
//        return mapToDTO(savedBusSchedule);
//    }
//
//    public BusScheduleResponseDTO updateBusSchedule(BusScheduleRequestDTO requestDTO, Long busScheduleId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Adding bus Schedule by email: " + email);
//
//        User currentUser = userRepository.findByEmail(email)
//                .orElseThrow(()-> new UserNotFoundException("User with email: " + email + " not found"));
//
//        Bus bus = busRepository.findById(requestDTO.getBusId())
//                .orElseThrow(()-> new UserNotFoundException("Bus with id: " + requestDTO.getBusId() + " not found"));
//
//        if(!bus.getOperator().getUser().getId().equals(currentUser.getId())) {
//            logger.error("User with email: {} is not operator of  bus ID: {}", email, requestDTO.getBusId());
//            throw new UserNotFoundException("User with email: " + email + " not found");
//        }
//
//        BusSchedule schedule= busScheduleRepository.findById(busScheduleId)
//                .orElseThrow(()-> new UserNotFoundException("Bus Schedule with id: " + busScheduleId + " not found"));
//
//        if(!schedule.getBus().getOperator().getUser().getId().equals(currentUser.getId())) {
//            logger.error("User with email: {} is not approved", email);
//            throw new UserNotFoundException("User with email: " + email + " is not approved");
//        }
//
//        schedule.setSource(requestDTO.getSource());
//        schedule.setDestination(requestDTO.getDestination());
//        schedule.setDepartureTime(requestDTO.getDepartureTime());
//        schedule.setArrivalTime(requestDTO.getArrivalTime());
//        schedule.setFare(requestDTO.getFare());
//        schedule.setAvailableSeats(requestDTO.getAvailableSeats());
//        BusSchedule savedBusSchedule = busScheduleRepository.save(schedule);
//        return mapToDTO(savedBusSchedule);
//
//    }
//
//    public void deleteBusSchedule(Long scheduleId) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("Deleting schedule by email: {}", email);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
//        if (!user.getRole().contains(Role.BUS_OPERATOR)) {
//            logger.warn("User with email: {} is not a bus operator", email);
//            throw new UnauthorizedRoleException("User with email: " + email + " is not a bus operator");
//        }
//
//        BusSchedule schedule = busScheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new UserNotFoundException("Schedule with id: " + scheduleId + " not found"));
//        if (!schedule.getBus().getOperator().getUser().getId().equals(user.getId())) {
//            logger.error("User with email: {} is not the operator of bus for schedule id: {}", email, scheduleId);
//            throw new UnauthorizedRoleException("User is not the operator of this bus");
//        }
//
//        busScheduleRepository.delete(schedule);
//        logger.info("Deleted schedule with id: {}", scheduleId);
//    }

//    public List<BusScheduleSearchResponseDTO> searchBusSchedules(BusScheduleSearchRequestDTO searchRequestDTO) {
//        logger.info("Searching  schedules  for source:{}, destination:{}, on Date :{}", searchRequestDTO.getSource(), searchRequestDTO.getDestination(), searchRequestDTO.getTravelDate());
//        LocalDate travelDate;
//        try{
//            travelDate = LocalDate.parse(searchRequestDTO.getTravelDate(), DateTimeFormatter.ISO_LOCAL_DATE);
//        }catch(Exception e){
//            logger.error("Travel date format :{} is incorrect", searchRequestDTO.getTravelDate());
//            throw new IllegalArgumentException("Travel date format is incorrect"+searchRequestDTO.getTravelDate());
//        }
//        LocalDateTime startOfDay =  travelDate.atStartOfDay();
//        LocalDateTime endOfDay = travelDate.plusDays(1).atStartOfDay();
//        List<BusSchedule> schedules = busScheduleRepository.findBySearchCriteria(
//                searchRequestDTO.getSource()
//                searchRequestDTO.getDestination(),
//                startOfDay,
//                endOfDay,
//                ApprovalStatus.APPROVED,
//                searchRequestDTO.getBusType(),
//                searchRequestDTO.getMinFare(),
//                searchRequestDTO.getMaxFare()
//        );
//        if (schedules.isEmpty()) {
//            logger.warn("No schedules found for the given criteria");
//        }
//        return  schedules.stream().map(this::mapSearchTODTO).collect(Collectors.toList());
//    }


    private BusScheduleResponseDTO mapToDTO(BusSchedule busSchedule) {
        BusScheduleResponseDTO busScheduleResponseDTO = new BusScheduleResponseDTO();
        busScheduleResponseDTO.setId(busSchedule.getId());
        busScheduleResponseDTO.setSource(busSchedule.getSource());
        busScheduleResponseDTO.setDestination(busSchedule.getDestination());
        busScheduleResponseDTO.setDepartureTime(busSchedule.getDepartureTime());
        busScheduleResponseDTO.setArrivalTime(busSchedule.getArrivalTime());
        busScheduleResponseDTO.setFare(busSchedule.getFare());
        busScheduleResponseDTO.setAvailableSeats(busSchedule.getAvailableSeats());
        return busScheduleResponseDTO;
    }

    private BusScheduleSearchResponseDTO mapSearchTODTO(BusSchedule busSchedule) {
        BusScheduleSearchResponseDTO dto = new BusScheduleSearchResponseDTO();
        dto.setBusScheduleId(busSchedule.getId());
        dto.setBusName(busSchedule.getBus().getName());
        dto.setBusType(busSchedule.getBus().getBusType());
        dto.setTotalSeats(busSchedule.getBus().getTotalSeats());
        dto.setAvailableSeats(busSchedule.getAvailableSeats());
        dto.setDepartureTime(busSchedule.getDepartureTime());
        dto.setFare(busSchedule.getFare());
        return dto;
    }
}
