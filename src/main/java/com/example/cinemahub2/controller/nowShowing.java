package com.example.cinemahub2.controller;

import com.example.cinemahub2.DTO.MovieTitleDTO;
import com.example.cinemahub2.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
