package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.MovieWithShowsDTO;
import com.example.cinemahub2.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movies")
public class Movie {

    @Autowired
    private MovieService movieService;


    @GetMapping("/{movieName}/shows")
    public MovieWithShowsDTO getMovieShows(@PathVariable String movieName) {
        return movieService.getMovieWithShowDetails(movieName); // ✅ correct return type
    }
}