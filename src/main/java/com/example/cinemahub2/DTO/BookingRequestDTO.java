package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor; // Automatically generates a constructor with all fields
import lombok.Data;             // Automatically generates getters, setters, toString, equals, and hashCode
import lombok.NoArgsConstructor;  // Automatically generates a no-argument constructor
import java.util.List; // For the list of seat numbers

@Data               // This annotation from Lombok replaces boilerplate like getters, setters, etc.
@NoArgsConstructor  // Creates a constructor with no arguments
@AllArgsConstructor // Creates a constructor that takes all fields as arguments
public class BookingRequestDTO {
    private Integer showId;         // The ID of the movie show the user wants to book seats for
    private List<String> seatNumbers; // A list of seat numbers (e.g., "A1", "A2", "B5") the user selected

    // IMPORTANT: The userId is deliberately ABSENT from this DTO.
    // It will be obtained securely from the authenticated user's JWT on the server-side.
}