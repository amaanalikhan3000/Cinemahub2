package com.example.cinemahub2.repository.user;

import com.example.cinemahub2.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
  //  List<Ticket> findByShow_ShowId(Integer showId);

    List<Ticket> findByShow_ShowId(Integer showId);
}
