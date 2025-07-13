package com.example.cinemahub2.repository.user;

import com.example.cinemahub2.entity.Seat;
import com.example.cinemahub2.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    Optional<Seat> findBySeatNumberAndTheater(String seatNumber, Theater theater);
    List<Seat> findByTheater_TheaterId(Integer theaterId);

    List<Seat> findByTheater(Theater theater);
}
