package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MovieTitleDTO {
    private String movieName;
    private Integer movieId;
}
