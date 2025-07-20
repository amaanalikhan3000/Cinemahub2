package com.example.cinemahub2.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.List;

@Entity
@Data // Includes @Getter, @Setter, @EqualsAndHashCode, @ToString (default)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "theater")
@ToString
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //theaterId is auto-generated
    private Integer theaterId;

    private String theaterName;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @ToString.Exclude // <<< this relationship from toString()
    @JsonIgnoreProperties({"theaters", "movies", "password"})
    private Admin admin;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude // << Exclude this collection from toString()
    @JsonIgnoreProperties("theater") // Correct for JSON serialization
    private List<MovieShow> shows;
}