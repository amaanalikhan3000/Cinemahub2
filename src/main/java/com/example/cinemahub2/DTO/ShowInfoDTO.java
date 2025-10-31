package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ShowInfoDTO {
    private Integer showId;
    private String theaterName;
    private LocalDate showDate;
    private LocalTime showTime;


}
