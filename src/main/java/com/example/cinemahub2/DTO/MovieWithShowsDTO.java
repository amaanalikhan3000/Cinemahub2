package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieWithShowsDTO {
    private String movieName;
    private String genre;
    private LocalTime duration;
    private List<ShowInfoDTO> shows;

}