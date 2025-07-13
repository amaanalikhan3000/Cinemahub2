package com.example.cinemahub2.entity;

import javax.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "theater")
@ToString(exclude = "admin")
public class Theater {

    @Id
    private Integer theaterId;

    private String theaterName;
    private String location;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "theater")
    private List<MovieShow> shows;
}
