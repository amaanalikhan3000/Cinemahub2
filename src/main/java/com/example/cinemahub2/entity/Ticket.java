package com.example.cinemahub2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data // Includes @Getter, @Setter, @EqualsAndHashCode, @ToString (default)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets", uniqueConstraints = {
        // Ensures that for a given movie show, a seat number can only be booked once.
        @UniqueConstraint(columnNames = {"show_id", "seat_number"})
})
@ToString // <<< --- Ensure no 'exclude' parameter here, just @ToString
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY loading for performance
    @JoinColumn(name = "show_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore //  Prevents circular JSON serialization and lazy loading issues
    private MovieShow show;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY loading for performance
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude // Exclude this relationship from toString()
    @JsonIgnore // Prevents circular JSON serialization and lazy loading issues
    private AppUser user;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    // This method will automatically set the bookingTime before a new Ticket entity is persisted.
    @PrePersist
    protected void onCreate() {
        if (this.bookingTime == null) {
            this.bookingTime = LocalDateTime.now();
        }
    }
}