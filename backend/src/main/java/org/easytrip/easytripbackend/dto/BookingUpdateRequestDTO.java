package org.easytrip.easytripbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateRequestDTO {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
