package com.example.cinemahub2.services;


import com.example.cinemahub2.DTO.MovieTitleDTO;
import com.example.cinemahub2.DTO.MovieWithShowsDTO;
import com.example.cinemahub2.DTO.ShowInfoDTO;
import com.example.cinemahub2.entity.Movie;
import com.example.cinemahub2.repository.user.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MovieService {

    @Autowired
    private MovieRepo movieRepo;




    public List<MovieTitleDTO> getTop5MovieTitles() {

        List<Movie> topMovies = movieRepo.findTop5ByOrderByMovieIdDesc();
        return topMovies.stream()
                .map(movie -> new MovieTitleDTO(movie.getMovieName(), movie.getMovieId()))
                .collect(Collectors.toList());
    }

    public MovieWithShowsDTO getMovieWithShowDetails(String movieName) {
        Optional<Movie> movieOptional = movieRepo.findByMovieName(movieName);

        if (movieOptional.isEmpty()) {
            throw new RuntimeException("Movie not found: " + movieName);
        }

        Movie movie = movieOptional.get();

        List<ShowInfoDTO> showInfos = movie.getShows().stream()
                .map(show -> new ShowInfoDTO(
                        show.getTheater().getTheaterName(),
                        show.getShowTime().toLocalDate(),
                        show.getShowTime().toLocalTime()
                ))
                .collect(Collectors.toList());

        return new MovieWithShowsDTO(
                movie.getMovieName(),
                movie.getGenre(),
                movie.getDuration(),
                showInfos
        );
    }

    // for pagination
    public Page<MovieTitleDTO> getPaginatedMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("movieId").descending());
        return movieRepo.findAllMovieTitles(pageable);
    }
}
