package com.example.cinemahub2.entity;


import javax.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "movieshow")
public class MovieShow {

    @Id
    private Integer showId;

    private LocalDateTime showTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @ToString.Exclude
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    @ToString.Exclude
    private Theater theater;

    @OneToMany(mappedBy = "show")
    @ToString.Exclude
    private List<Ticket> tickets;


    private LocalDate showDate;
}
