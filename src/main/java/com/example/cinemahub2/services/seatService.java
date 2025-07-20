package com.example.cinemahub2.services;


import com.example.cinemahub2.DTO.BookingDetailResponseDto;
import com.example.cinemahub2.DTO.SeatAvailabilityDTO;
import com.example.cinemahub2.entity.*;
import com.example.cinemahub2.repository.user.MovieShowRepo;
import com.example.cinemahub2.repository.user.SeatRepository;
import com.example.cinemahub2.repository.user.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.cinemahub2.repository.user.userRepo;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private userRepo userRepo;


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




    /**
     * Books a list of seats for a specific movie show, ensuring concurrency and transaction management.
     *
     * @param showId      The ID of the movie show.
     * @param seatNumbers A list of seat numbers to be booked.
     * @param userId      The ID of the user making the booking (obtained securely from JWT).
     * @return A list of successfully created Ticket entities.
     * @throws RuntimeException if the show or user is not found, or if any seat is already booked concurrently.
     */


    @Transactional
    public BookingDetailResponseDto bookSeats(Integer showId, List<String> seatNumbers, String userId) {
        MovieShow show = movieShowRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with ID: " + showId));

        AppUser appUser = userRepo.findByEmail(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        logger.info("Attempting to book seats {} for show ID: {} by user ID: {}", seatNumbers, showId, userId);

        List<Ticket> ticketsToBook = new ArrayList<>();

        for (String seatNumber : seatNumbers) {
            if (ticketRepo.findByShow_ShowIdAndSeatNumber(showId, seatNumber).isPresent()) {
                logger.warn("Seat '{}' for show '{}' is already booked (pre-check). Rolling back transaction.", seatNumber, showId);
                throw new RuntimeException("Seat " + seatNumber + " for show " + showId + " is already booked. Please choose another.");
            }

            Ticket ticket = new Ticket();
            ticket.setShow(show);
            ticket.setSeatNumber(seatNumber);
            ticket.setUser(appUser);
            ticket.setPrice(BigDecimal.valueOf(10.00));
            ticketsToBook.add(ticket);
        }

        try {
            List<Ticket> savedTickets = ticketRepo.saveAll(ticketsToBook);
            logger.info("Successfully booked {} seats for show {}. Ticket IDs: {}",
                    savedTickets.size(), showId,
                    savedTickets.stream().map(Ticket::getTicketId).collect(Collectors.toList()));

            return buildBookingDetailResponse(show, savedTickets,ticketsToBook.getFirst().getPrice());


        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to book seats for show {} due to concurrent booking (DataIntegrityViolation): {}", showId, e.getMessage());
            throw new RuntimeException("One or more selected seats were just booked by someone else. Please review your selection and try again.", e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred during seat booking for show {}: {}", showId, e.getMessage());
            throw new RuntimeException("An unexpected error occurred during booking. Please try again.", e);
        }
    }


        public BookingDetailResponseDto buildBookingDetailResponse(MovieShow show, List<Ticket> bookedTickets, BigDecimal totalPayment) {
            String ticketNumbers = bookedTickets.stream()
                    .map(Ticket::getSeatNumber)
                    .collect(Collectors.joining(", "));

            BookingDetailResponseDto.ScheduleDto scheduleDto = new BookingDetailResponseDto.ScheduleDto(
                    show.getMovie().getMovieName(),
                    show.getShowDate(),
                    ticketNumbers,
                    show.getShowStartTime()
            );

            BookingDetailResponseDto.TransactionDetailDto transactionDto = new BookingDetailResponseDto.TransactionDetailDto(
                    bookedTickets.get(0).getPrice(),
                    bookedTickets.size(),
                    new BigDecimal("15.00"),
                    totalPayment
            );

            return BookingDetailResponseDto.builder()
                    .schedule(scheduleDto)
                    .transactionDetail(transactionDto)
                    .cancellationPolicy("No cancellation after 30 mins before showtime")
                    .build();
        }



}