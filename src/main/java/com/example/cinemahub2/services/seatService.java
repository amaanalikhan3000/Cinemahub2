package com.example.cinemahub2.services;


import com.example.cinemahub2.DTO.SeatAvailabilityDTO;
import com.example.cinemahub2.entity.MovieShow;
import com.example.cinemahub2.entity.Seat;
import com.example.cinemahub2.entity.Theater;
import com.example.cinemahub2.entity.Ticket;
import com.example.cinemahub2.repository.user.MovieShowRepo;
import com.example.cinemahub2.repository.user.SeatRepository;
import com.example.cinemahub2.repository.user.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class seatService {

    @Autowired
    private MovieShowRepo movieShowRepo;


    @Autowired
    private SeatRepository seatRepo;


    @Autowired
    private TicketRepository ticketRepo;

    private static final Logger logger = LoggerFactory.getLogger(seatService.class);

    public List<SeatAvailabilityDTO> getAvailableSeatsForShow(Integer showId) {
        MovieShow show = movieShowRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));


        Theater theater = show.getTheater();
        logger.info("show theater {}",theater);

        List<Seat> allSeats = seatRepo.findByTheater(theater);  // All seats in that theater

        logger.info("SEATS {}",allSeats);



        List<Ticket> bookedTickets = ticketRepo.findByShow_ShowId(showId); // All tickets already booked

        logger.info("booked SEATS {}",bookedTickets);

        Set<String> bookedSeatNumbers = bookedTickets.stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toSet());
        logger.info("List of Booked Seat Numbers: {}", bookedSeatNumbers);

        return allSeats.stream()
                .map(seat -> new SeatAvailabilityDTO(
                        seat.getSeatNumber(),
                        bookedSeatNumbers.contains(seat.getSeatNumber())
                ))
                .collect(Collectors.toList());
    }
}
