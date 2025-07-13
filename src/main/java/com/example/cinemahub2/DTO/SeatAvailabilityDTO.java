package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAvailabilityDTO {
    private String seatNumber;
    private boolean booked;
}
