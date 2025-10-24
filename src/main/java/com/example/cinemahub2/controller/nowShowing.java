package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.MovieTitleDTO;
import com.example.cinemahub2.Exception.ExceptionsHandler.ResourceNotFoundException;
import com.example.cinemahub2.entity.Movie;
import com.example.cinemahub2.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nowShowingMovie")
public class nowShowing {

    @Autowired
    private MovieService movieService;

    @GetMapping("/top5titles")
    public ResponseEntity<?> getTop5Titles() {
        List<MovieTitleDTO> titles = movieService.getTop5MovieTitles();
        if (!titles.isEmpty()) {
            return new ResponseEntity<>(titles, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("");
        }
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<MovieTitleDTO> moviePage = movieService.getPaginatedMovies(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("movies", moviePage.getContent());
        response.put("currentPage", moviePage.getNumber());
        response.put("totalItems", moviePage.getTotalElements());
        response.put("totalPages", moviePage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
