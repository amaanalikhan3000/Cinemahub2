package com.example.cinemahub2.entity;



import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class Ticket {

    @Id
    private Integer ticketId;
    private String seatNumber;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private MovieShow show;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}
