package org.easytrip.easytripbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPriceResponseDTO {
    private Long guesthouseId;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;
    private List<String> paymentOptions;
}
