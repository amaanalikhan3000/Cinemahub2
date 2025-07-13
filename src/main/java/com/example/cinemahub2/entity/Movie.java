package com.example.cinemahub2.entity;



import lombok.*;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class Movie {

    @Id
    private Integer movieId;

    private String movieName;
    private String genre;
    private LocalTime duration;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    private Admin admin;

    @OneToMany(mappedBy = "movie")
    @ToString.Exclude
    private List<MovieShow> shows;
}
