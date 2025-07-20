package com.example.cinemahub2.repository.user;

import com.example.cinemahub2.DTO.TicketDetailsDTO;
import com.example.cinemahub2.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByShow_ShowId(Integer showId);

    Optional<Object> findByShow_ShowIdAndSeatNumber(Integer showId, String seatNumber);

    @Query("SELECT t FROM Ticket t " +
            "JOIN FETCH t.show s " +
            "JOIN FETCH s.movie m " +
            "JOIN FETCH s.theater th " +
            "WHERE t.user.userId = :userId " +
            "ORDER BY t.bookingTime DESC")
    List<Ticket> findLatestTicketByUserId(@Param("userId") Integer userId);

}
