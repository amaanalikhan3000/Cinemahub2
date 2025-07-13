package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ShowInfoDTO {
    private String theaterName;
//    private LocalDate showDate;
//    private LocalTime showTime;

    private LocalDate showDate;
    private LocalTime showTime;

//    public ShowInfoDTO(String theaterName, LocalTime localTime, LocalDate showDate) {
//    }
}
