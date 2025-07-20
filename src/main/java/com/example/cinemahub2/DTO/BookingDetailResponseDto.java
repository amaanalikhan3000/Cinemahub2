package com.example.cinemahub2.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDetailResponseDto {
    private ScheduleDto schedule;
    private TransactionDetailDto transactionDetail;
    private String cancellationPolicy;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDto {
        private String movieTitle;
        @JsonFormat(pattern = "E, dd MMM yyyy", shape = JsonFormat.Shape.STRING)
        private LocalDate date;
        private String tickets; // e.g., "C8, C9"
        private LocalTime hours;
    }





    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDetailDto {
        private BigDecimal regularSeatPrice;
        private int numberOfTickets;
        private BigDecimal serviceChargePerTicket;
        private BigDecimal totalPayment;
    }
}
