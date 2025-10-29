package com.example.cinemahub2.services;

import com.example.cinemahub2.DTO.MovieTitleDTO;
import com.example.cinemahub2.DTO.MovieWithShowsDTO;
import com.example.cinemahub2.Exception.ExceptionsHandler.ResourceNotFoundException;
import com.example.cinemahub2.entity.Movie;
import com.example.cinemahub2.entity.MovieShow;
import com.example.cinemahub2.entity.Theater;
import com.example.cinemahub2.repository.user.MovieRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepo movieRepo;

    @InjectMocks
    private MovieService movieService;

    @Test
    void testGetTop5MovieTitles_ReturnsTop5Movies() {
        // Arrange
        Movie movie = new Movie();
        movie.setMovieName("Movie A");

        Movie movie2 = new Movie();
        movie2.setMovieName("Movie B");

        List<Movie> mockMovies = Arrays.asList(
                movie, movie2
        );
        when(movieRepo.findTop5ByOrderByMovieIdDesc()).thenReturn(mockMovies);

        // Act
        List<MovieTitleDTO> result = movieService.getTop5MovieTitles();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Movie A", result.get(0).getMovieName());
        assertEquals("Movie B", result.get(1).getMovieName());
        verify(movieRepo, times(1)).findTop5ByOrderByMovieIdDesc();
    }

    @Test
    void testGetTop5MovieTitles_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(movieRepo.findTop5ByOrderByMovieIdDesc()).thenReturn(Collections.emptyList());

        // Act
        List<MovieTitleDTO> result = movieService.getTop5MovieTitles();

        // Assert
        assertTrue(result.isEmpty());
        verify(movieRepo, times(1)).findTop5ByOrderByMovieIdDesc();
    }

    @Test
    void testGetMovieWithShowDetails_MovieFound_ReturnsMovieWithShowsDTO() {
        // Arrange
        String movieName = "Movie A";
        MovieShow movieShow = new MovieShow();
        Theater theater = new Theater();
        theater.setTheaterName("Theater X");
        movieShow.setTheater(theater);
        movieShow.setShowTime(LocalDateTime.of(2025, 10, 25, 14, 0));

        Movie mockMovie = new Movie();
        mockMovie.setGenre("Action");
        mockMovie.setMovieName(movieName);
        mockMovie.setShows(List.of(movieShow));

        when(movieRepo.findByMovieName(movieName)).thenReturn(Optional.of(mockMovie));

        // Act
        MovieWithShowsDTO result = movieService.getMovieWithShowDetails(movieName);

        // Assert
        assertEquals(movieName, result.getMovieName());
        assertEquals("Action", result.getGenre());
        assertEquals(1, result.getShows().size());
        assertEquals("Theater X", result.getShows().get(0).getTheaterName());
        assertEquals(LocalDate.of(2025, 10, 25), result.getShows().get(0).getShowDate());
        assertEquals(LocalTime.of(14, 0), result.getShows().get(0).getShowTime());
        verify(movieRepo, times(1)).findByMovieName(movieName);
    }

    @Test
    void testGetMovieWithShowDetails_MovieNotFound_ThrowsResourceNotFoundException() {
        // Arrange
        String movieName = "Nonexistent Movie";
        when(movieRepo.findByMovieName(movieName)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> movieService.getMovieWithShowDetails(movieName));
        assertEquals("Movie not found: " + movieName, exception.getMessage());
        verify(movieRepo, times(1)).findByMovieName(movieName);
    }

    @Test
    void testGetPaginatedMovies_ReturnsPaginatedMovies() {
        // Arrange
        int page = 0;
        int size = 2;

        Pageable pageable = PageRequest.of(page, size, Sort.by("movieId").descending());
        List<MovieTitleDTO> mockMovies = Arrays.asList(
                new MovieTitleDTO("Movie A", 1),
                new MovieTitleDTO("Movie B", 2)
        );
        Page<MovieTitleDTO> mockPage = new PageImpl<>(mockMovies, pageable, mockMovies.size());
        when(movieRepo.findAllMovieTitles(pageable)).thenReturn(mockPage);

        // Act
        Page<MovieTitleDTO> result = movieService.getPaginatedMovies(page, size);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals("Movie A", result.getContent().get(0).getMovieName());
        assertEquals(1, result.getContent().get(0).getMovieId());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        assertEquals(1, result.getTotalPages());
        verify(movieRepo, times(1)).findAllMovieTitles(pageable);
    }

    @Test
    void testGetPaginatedMovies_EmptyPage_ReturnsEmptyPage() {
        // Arrange
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, Sort.by("movieId").descending());
        Page<MovieTitleDTO> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(movieRepo.findAllMovieTitles(pageable)).thenReturn(emptyPage);

        // Act
        Page<MovieTitleDTO> result = movieService.getPaginatedMovies(page, size);

        // Assert
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        assertEquals(0, result.getTotalElements());
        verify(movieRepo, times(1)).findAllMovieTitles(pageable);
    }
}