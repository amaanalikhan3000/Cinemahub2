package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.TicketDetailsDTO;
import com.example.cinemahub2.entity.AppUser;
import com.example.cinemahub2.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.cinemahub2.repository.user.UserRepo;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;


@RestController
@RequestMapping("/ticket")
public class ticketController {

    @Autowired
    TicketService ticketService;


    @Autowired
    private UserRepo userRepo;

    @GetMapping("/viewTicket")
    public ResponseEntity<?> viewLatestTicket() {

        String email = ticketService.getLoggedInEmail(); // from SecurityContext
        Optional<AppUser> userOpt = userRepo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user.");
        }
        Integer userId = userOpt.get().getUserId();
        Optional<TicketDetailsDTO> latestTicket = ticketService.findLatestTicketForUser(userId);
        return latestTicket
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No ticket found for user."));
    }


}

