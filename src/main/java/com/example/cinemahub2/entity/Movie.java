package com.example.cinemahub2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude
    @JsonIgnoreProperties({"movies", "theaters", "password"})
    private Admin admin;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY) // Always specify FetchType for collections
    @ToString.Exclude

    @JsonIgnoreProperties("movie")
    private List<MovieShow> shows;
}