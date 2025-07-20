package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.MovieWithShowsDTO;
import com.example.cinemahub2.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Movie {

    @Autowired
    private MovieService movieService;


    @GetMapping("/movies/{movieName}/shows")
    public MovieWithShowsDTO getMovieShows(@PathVariable String movieName) {
        return movieService.getMovieWithShowDetails(movieName); // ✅ correct return type
    }
}
