package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.BookingDetailResponseDto;
import com.example.cinemahub2.DTO.BookingRequestDTO;
import com.example.cinemahub2.DTO.SeatAvailabilityDTO;
import com.example.cinemahub2.services.seatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/bookings")
@RestController
public class seatController {


    @Autowired
    private seatService seatService;
    private static final Logger logger = LoggerFactory.getLogger(seatController.class);

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<SeatAvailabilityDTO>> getAvailableSeats(@PathVariable Integer showId) {
        List<SeatAvailabilityDTO> seats = seatService.getAvailableSeatsForShow(showId);
        return ResponseEntity.ok(seats);
    }


    @PostMapping("/book-seats")
    public ResponseEntity<?> bookSeats(@RequestBody BookingRequestDTO bookingRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated or session expired. Please log in.");
        }

        String username;
        try {

            Object principal = authentication.getPrincipal();


            if (principal instanceof UserDetails) {

                username = ((UserDetails) principal).getUsername();

            } else if (principal instanceof String) {

                username = (String) principal;
            } else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Could not determine user ID from authentication context.");
            }
        } catch (NumberFormatException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid user ID format in authentication context.");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user ID from authentication: " + e.getMessage());
        }

        try {

            BookingDetailResponseDto bookingDetails = seatService.bookSeats(
                    bookingRequest.getShowId(),
                    bookingRequest.getSeatNumbers(),
                    username // Pass the verified userId to the service
            );

            logger.info("TICKET"+ bookingDetails);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingDetails);
        } catch (RuntimeException e) {

            if (e.getMessage().contains("Show not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else if (e.getMessage().contains("already booked")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Booking failed: " + e.getMessage()); // 500 Internal Server Error
        }
    }



}
