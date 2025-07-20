package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailsDTO {
    private LocalDateTime bookingTime;
    private String seatNumber;
    private BigDecimal price;
    private LocalDateTime showTime;

    private MovieInfo movie;
    private TheaterInfo theater;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovieInfo {
        private String title;
        private String duration; // formatted like "2h 49m"
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TheaterInfo {
        private String name;
        private String location;
    }
}

