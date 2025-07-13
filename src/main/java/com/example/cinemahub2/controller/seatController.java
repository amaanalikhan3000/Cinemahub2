package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.SeatAvailabilityDTO;
import com.example.cinemahub2.services.seatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class seatController {


    @Autowired
    private seatService seatService;

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<SeatAvailabilityDTO>> getAvailableSeats(@PathVariable Integer showId) {
        List<SeatAvailabilityDTO> seats = seatService.getAvailableSeatsForShow(showId);
        return ResponseEntity.ok(seats);
    }



}
