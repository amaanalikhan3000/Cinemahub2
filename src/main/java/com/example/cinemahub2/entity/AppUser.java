package com.example.cinemahub2.entity;

import com.example.cinemahub2.configAndUtility.RolesConverter;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email")
    @Column(unique = true)
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be 8+ characters, include upper & lower case letters, a number, and a special character"
    )
    private String password;
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude // Exclude this collection from toString()
    private List<Ticket> tickets;

    @Column(name = "is_logged_in")
    private boolean isLoggedIn;

    @Convert(converter = RolesConverter.class)
    private List<String> roles;
}