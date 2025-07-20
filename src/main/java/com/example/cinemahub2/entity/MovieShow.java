package com.example.cinemahub2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data // Includes @Getter, @Setter, @EqualsAndHashCode, @ToString (default)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movieshow")
@ToString
public class MovieShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showId;

    private LocalDateTime showTime;

    @Transient
    private LocalDate showDate;
    @Transient
    private LocalTime showStartTime;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY for ManyToOne
    @JoinColumn(name = "movie_id")
    @ToString.Exclude //
    @JsonIgnoreProperties("shows") // 'Movie' has a 'shows' collection that references MovieShow
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    @ToString.Exclude //  Exclude this relationship from toString()
    // Ignore the 'shows' list (or 'movieShows' if that's what it's named) on the 'theater' object to prevent circular serialization
    @JsonIgnoreProperties("shows") // Assuming 'Theater' has a 'shows' collection
    private Theater theater;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // Add cascade and orphanRemoval
    @ToString.Exclude // Exclude this collection from toString()
    private List<Ticket> tickets;

    // Helper methods for deriving showDate and showStartTime if @Transient
    public LocalDate getShowDate() {
        return this.showTime != null ? this.showTime.toLocalDate() : null;
    }

    public LocalTime getShowStartTime() {
        return this.showTime != null ? this.showTime.toLocalTime() : null;
    }
}