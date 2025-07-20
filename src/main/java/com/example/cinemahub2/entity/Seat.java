package com.example.cinemahub2.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data // Includes @Getter, @Setter, @EqualsAndHashCode, @ToString (default)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"seat_number", "theater_id"})
})
@ToString // Keep just the annotation here, no exclude parameter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    @Column(name = "seat_number")
    private String seatNumber; // E.g., A1, B2, etc.

    @ManyToOne(fetch = FetchType.LAZY) //  FetchType.LAZY for performance
    @JoinColumn(name = "theater_id")
    @ToString.Exclude // Exclude this relationship from toString()
    private Theater theater;
}