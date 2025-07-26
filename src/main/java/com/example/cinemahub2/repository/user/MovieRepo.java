package com.example.cinemahub2.repository.user;


import com.example.cinemahub2.DTO.MovieTitleDTO;
import com.example.cinemahub2.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Integer> {
    List<Movie> findTop5ByOrderByMovieIdDesc();
    Optional<Movie> findByMovieName(String movieName);

    @Query("SELECT new com.example.cinemahub2.DTO.MovieTitleDTO(m.movieName, m.movieId) FROM Movie m")
    Page<MovieTitleDTO> findAllMovieTitles(Pageable pageable);
}
