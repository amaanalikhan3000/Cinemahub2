package com.example.cinemahub2.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seatNumber", "theater_id"})
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    private String seatNumber; // E.g., A1, B2, etc.

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;
}

