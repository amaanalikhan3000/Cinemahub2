package com.example.cinemahub2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data // Includes @Getter, @Setter, @EqualsAndHashCode, @ToString (default)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "admin")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "theaters", "movies"}) // Added 'theaters' and 'movies' to ignore during JSON serialization by default
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming adminId is auto-generated
    private Integer adminId;

    private String adminName;
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude // Exclude this collection from toString()
    @JsonIgnoreProperties("admin") // Continue to ignore 'admin' in the Theater object during serialization
    private List<Theater> theaters;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude // Exclude this collection from toString()
    @JsonIgnoreProperties("admin") // Continue to ignore 'admin' in the Movie object during serialization
    private List<Movie> movies;
}