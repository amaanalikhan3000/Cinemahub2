package com.example.cinemahub2.entity;



import com.example.cinemahub2.configAndUtility.RolesConverter;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    @Column(unique = true)
    private String email;

    private String password;
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @Column(name = "is_logged_in")
    private boolean isLoggedIn;

    @Convert(converter = RolesConverter.class)
    private List<String> roles;
}
