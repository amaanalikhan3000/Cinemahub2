package com.example.cinemahub2.services;

import com.example.cinemahub2.DTO.BookingDetailResponseDto;
import com.example.cinemahub2.DTO.SeatAvailabilityDTO;
import com.example.cinemahub2.Exception.ExceptionsHandler.BookingConflictException;
import com.example.cinemahub2.Exception.ExceptionsHandler.InvalidRequestException;
import com.example.cinemahub2.Exception.ExceptionsHandler.ResourceNotFoundException;
import com.example.cinemahub2.Exception.ExceptionsHandler.UserNotFoundException;
import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.entity.Movie;
import com.example.cinemahub2.entity.MovieShow;
import com.example.cinemahub2.entity.Seat;
import com.example.cinemahub2.entity.Theater;
import com.example.cinemahub2.entity.Ticket;
import com.example.cinemahub2.repository.user.MovieShowRepo;
import com.example.cinemahub2.repository.user.SeatRepository;
import com.example.cinemahub2.repository.user.TicketRepository;
import com.example.cinemahub2.repository.user.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
    @Mock
    private MovieShowRepo movieShowRepo;

    @Mock
    private SeatRepository seatRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private TicketRepository ticketRepo;

    @InjectMocks
    private SeatService seatService;

    @Test
    void testGetAvailableSeatsForShow_ShowExists_ReturnsSeatAvailability() {
        // Arrange
        Integer showId = 1;
        Theater theater = new Theater();
        theater.setTheaterName("Theater A");
        Movie movie = new Movie();
        movie.setMovieName("Movie A");
        MovieShow show = new MovieShow();
        show.setShowId(showId);
        show.setMovie(movie);
        show.setTheater(theater);
        List<Seat> seats = Arrays.asList(
                new Seat(1, "A1", theater),
                new Seat(2, "A2", theater)
        );
        Ticket ticket = new Ticket();
        ticket.setShow(show);
        ticket.setSeatNumber("A1");
        ticket.setPrice(BigDecimal.valueOf(10.00));
        List<Ticket> bookedTickets = Collections.singletonList(ticket);
        Set<String> bookedSeatNumbers = bookedTickets.stream().map(Ticket::getSeatNumber).collect(Collectors.toSet());

        when(movieShowRepo.findById(showId)).thenReturn(Optional.of(show));
        when(seatRepo.findByTheater(theater)).thenReturn(seats);
        when(ticketRepo.findByShow_ShowId(showId)).thenReturn(bookedTickets);

        // Act
        List<SeatAvailabilityDTO> result = seatService.getAvailableSeatsForShow(showId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("A1", result.get(0).getSeatNumber());
        assertTrue(result.get(0).isBooked());
        assertEquals("A2", result.get(1).getSeatNumber());
        assertFalse(result.get(1).isBooked());
        verify(movieShowRepo, times(1)).findById(showId);
        verify(seatRepo, times(1)).findByTheater(theater);
        verify(ticketRepo, times(1)).findByShow_ShowId(showId);
    }

    @Test
    void testGetAvailableSeatsForShow_ShowNotFound_ThrowsRuntimeException() {
        // Arrange
        Integer showId = 1;
        when(movieShowRepo.findById(showId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> seatService.getAvailableSeatsForShow(showId));
        assertEquals("Show not found", exception.getMessage());
        verify(movieShowRepo, times(1)).findById(showId);
        verifyNoInteractions(seatRepo, ticketRepo);
    }

    @Test
    void testBookSeats_ValidInput_BooksSeatsSuccessfully() {
        // Arrange
        Integer showId = 1;
        String userId = "user@example.com";
        List<String> seatNumbers = Arrays.asList("A1", "A2");
        Theater theater = new Theater();
        theater.setTheaterName("Theater A");
        Movie movie = new Movie();
        movie.setMovieName("Movie A");
        MovieShow show = new MovieShow();
        show.setShowId(showId);
        show.setMovie(movie);
        show.setTheater(theater);
        AppUser user = new AppUser();
        user.setUserName(userId);
        Ticket ticket = new Ticket();
        ticket.setShow(show);
        ticket.setSeatNumber("A1");
        ticket.setPrice(BigDecimal.valueOf(10.00));
        Ticket ticket2 = new Ticket();
        ticket2.setShow(show);
        ticket2.setSeatNumber("A2");
        ticket2.setPrice(BigDecimal.valueOf(10.00));
        List<Ticket> savedTickets = Arrays.asList(
                ticket,
                ticket2
        );

        when(movieShowRepo.findById(showId)).thenReturn(Optional.of(show));
        when(userRepo.findByEmail(userId)).thenReturn(Optional.of(user));
        when(ticketRepo.findByShow_ShowIdAndSeatNumber(showId, "A1")).thenReturn(Optional.empty());
        when(ticketRepo.findByShow_ShowIdAndSeatNumber(showId, "A2")).thenReturn(Optional.empty());
        when(ticketRepo.saveAll(anyList())).thenReturn(savedTickets);

        // Act
        BookingDetailResponseDto result = seatService.bookSeats(showId, seatNumbers, userId);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(10.00), result.getTransactionDetail().getTotalPayment());
        assertEquals("No cancellation after 30 mins before showtime", result.getCancellationPolicy());
        verify(movieShowRepo, times(1)).findById(showId);
        verify(userRepo, times(1)).findByEmail(userId);
        verify(ticketRepo, times(2)).findByShow_ShowIdAndSeatNumber(eq(showId), anyString());
        verify(ticketRepo, times(1)).saveAll(anyList());
    }

    @Test
    void testBookSeats_ShowNotFound_ThrowsResourceNotFoundException() {
        // Arrange
        Integer showId = 1;
        String userId = "user@example.com";
        List<String> seatNumbers = Arrays.asList("A1");
        when(movieShowRepo.findById(showId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> seatService.bookSeats(showId, seatNumbers, userId));
        assertEquals("Show not found with ID: " + showId, exception.getMessage());
        verify(movieShowRepo, times(1)).findById(showId);
        verifyNoInteractions(userRepo, ticketRepo);
    }

    @Test
    void testBookSeats_UserNotFound_ThrowsUserNotFoundException() {
        // Arrange
        Integer showId = 1;
        String userId = "user@example.com";
        List<String> seatNumbers = Arrays.asList("A1");
        MovieShow show = new MovieShow();
        show.setShowId(showId);
        Movie movie = new Movie();
        movie.setMovieName("Movie A");
        show.setMovie(movie);
        Theater theater = new Theater();
        theater.setTheaterName("Theater A");
        show.setTheater(theater);
        show.setShowStartTime(LocalTime.of(14, 0));
        show.setShowDate(LocalDate.of(2025, 10, 25));
        when(movieShowRepo.findById(showId)).thenReturn(Optional.of(show));
        when(userRepo.findByEmail(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> seatService.bookSeats(showId, seatNumbers, userId));
        assertEquals("User not found with ID: " + userId, exception.getMessage());
        verify(movieShowRepo, times(1)).findById(showId);
        verify(userRepo, times(1)).findByEmail(userId);
        verifyNoInteractions(ticketRepo);
    }

    @Test
    void testBookSeats_SeatAlreadyBooked_ThrowsInvalidRequestException() {
        // Arrange
        Integer showId = 1;
        String userId = "user@example.com";
        List<String> seatNumbers = Arrays.asList("A1");
        MovieShow show = new MovieShow();
        show.setShowId(showId);
        Movie movie = new Movie();
        movie.setMovieName("Movie A");
        show.setMovie(movie);
        Theater theater = new Theater();
        theater.setTheaterName("Theater A");
        show.setTheater(theater);
        show.setShowStartTime(LocalTime.of(14, 0));
        show.setShowDate(LocalDate.of(2025, 10, 25));
        AppUser user = new AppUser();
        user.setUserName(userId);
        Ticket bookedTicket = new Ticket();
        bookedTicket.setShow(show);
        bookedTicket.setSeatNumber("A1");
        bookedTicket.setUser(user);
        bookedTicket.setPrice(BigDecimal.valueOf(10.00));

        when(movieShowRepo.findById(showId)).thenReturn(Optional.of(show));
        when(userRepo.findByEmail(userId)).thenReturn(Optional.of(user));
        when(ticketRepo.findByShow_ShowIdAndSeatNumber(showId, "A1")).thenReturn(Optional.of(bookedTicket));

        // Act & Assert
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> seatService.bookSeats(showId, seatNumbers, userId));
        assertEquals("Seat A1 for show 1 is already booked. Please choose another.", exception.getMessage());
        verify(movieShowRepo, times(1)).findById(showId);
        verify(userRepo, times(1)).findByEmail(userId);
        verify(ticketRepo, times(1)).findByShow_ShowIdAndSeatNumber(showId, "A1");
        verify(ticketRepo, never()).saveAll(anyList());
    }

    @Test
    void testBookSeats_ConcurrentBooking_ThrowsBookingConflictException() {
        // Arrange
        Integer showId = 1;
        String userId = "user@example.com";
        List<String> seatNumbers = Arrays.asList("A1");
        MovieShow show = new MovieShow();
        show.setShowId(showId);
        Movie movie = new Movie();
        movie.setMovieName("Movie A");
        show.setMovie(movie);
        Theater theater = new Theater();
        theater.setTheaterName("Theater A");
        show.setTheater(theater);
        show.setShowStartTime(LocalTime.of(14, 0));
        show.setShowDate(LocalDate.of(2025, 10, 25));
        AppUser user = new AppUser();
        user.setUserName(userId);
        when(movieShowRepo.findById(showId)).thenReturn(Optional.of(show));
        when(userRepo.findByEmail(userId)).thenReturn(Optional.of(user));
        when(ticketRepo.findByShow_ShowIdAndSeatNumber(showId, "A1")).thenReturn(Optional.empty());
        when(ticketRepo.saveAll(anyList())).thenThrow(new DataIntegrityViolationException("Concurrent booking detected"));

        // Act & Assert
        BookingConflictException exception = assertThrows(BookingConflictException.class,
                () -> seatService.bookSeats(showId, seatNumbers, userId));
        assertEquals("One or more selected seats were just booked by someone else. Please review your selection and try again.", exception.getMessage());
        verify(movieShowRepo, times(1)).findById(showId);
        verify(userRepo, times(1)).findByEmail(userId);
        verify(ticketRepo, times(1)).findByShow_ShowIdAndSeatNumber(showId, "A1");
        verify(ticketRepo, times(1)).saveAll(anyList());
    }

    @Test
    void testBuildBookingDetailResponse_ReturnsCorrectDto() {
        // Arrange
        Integer showId = 1;
        MovieShow show = new MovieShow();
        show.setShowId(showId);
        Movie movie = new Movie();
        movie.setMovieName("Movie A");
        show.setMovie(movie);
        Theater theater = new Theater();
        theater.setTheaterName("Theater A");
        show.setTheater(theater);
        show.setShowStartTime(LocalTime.of(14, 0));
        show.setShowDate(LocalDate.of(2025, 10, 25));
        AppUser user = new AppUser();
        user.setUserName("user@example.com");
        Ticket ticket = new Ticket();
        ticket.setShow(show);
        ticket.setSeatNumber("A1");
        ticket.setUser(user);
        ticket.setPrice(BigDecimal.valueOf(10.00));
        Ticket ticket2 = new Ticket();
        ticket2.setShow(show);
        ticket2.setSeatNumber("A2");
        ticket2.setUser(user);
        ticket2.setPrice(BigDecimal.valueOf(10.00));
        List<Ticket> bookedTickets = Arrays.asList(
                ticket, ticket2
        );
        BigDecimal totalPayment = BigDecimal.valueOf(10.00);

        // Act
        BookingDetailResponseDto result = seatService.buildBookingDetailResponse(show, bookedTickets, totalPayment);

        // Assert
        assertNotNull(result);
        assertEquals(totalPayment, result.getTransactionDetail().getTotalPayment());
        assertEquals("No cancellation after 30 mins before showtime", result.getCancellationPolicy());
    }
}