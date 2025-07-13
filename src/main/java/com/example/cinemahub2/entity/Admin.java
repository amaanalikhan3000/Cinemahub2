package com.example.cinemahub2.entity;




import lombok.*;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "theater")
@Table(name = "admin")
public class Admin {

    @Id
    private Integer adminId;

    private String adminName;
    private String password;

    @OneToMany(mappedBy = "admin")
    private List<Theater> theaters;

    @OneToMany(mappedBy = "admin")
    private List<Movie> movies;
}
