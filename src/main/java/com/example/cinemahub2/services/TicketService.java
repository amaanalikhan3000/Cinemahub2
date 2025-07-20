package com.example.cinemahub2.services;


import com.example.cinemahub2.DTO.TicketDetailsDTO;
import com.example.cinemahub2.entity.Movie;
import com.example.cinemahub2.entity.MovieShow;
import com.example.cinemahub2.entity.Theater;
import com.example.cinemahub2.entity.Ticket;
import com.example.cinemahub2.repository.user.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;



    public Optional<TicketDetailsDTO> findLatestTicketForUser(Integer userId) {
        List<Ticket> tickets = ticketRepository.findLatestTicketByUserId(userId);
        if (tickets.isEmpty()) return Optional.empty();

        Ticket ticket = tickets.get(0);
        TicketDetailsDTO dto = new TicketDetailsDTO();

        dto.setBookingTime(ticket.getBookingTime());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPrice(ticket.getPrice());

        MovieShow show = ticket.getShow();
        dto.setShowTime(show.getShowTime());

        // Movie Info
        Movie movie = show.getMovie();
        String durationFormatted = formatDuration(movie.getDuration());
        dto.setMovie(new TicketDetailsDTO.MovieInfo(movie.getMovieName(), durationFormatted));

        // Theater Info
        Theater theater = show.getTheater();
        dto.setTheater(new TicketDetailsDTO.TheaterInfo(theater.getTheaterName(), theater.getLocation()));

        return Optional.of(dto);
    }

    private String formatDuration(LocalTime duration) {
        if (duration == null) return "";
        int hours = duration.getHour();
        int minutes = duration.getMinute();
        return String.format("%dh %02dm", hours, minutes);
    }


    public String getLoggedInEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
