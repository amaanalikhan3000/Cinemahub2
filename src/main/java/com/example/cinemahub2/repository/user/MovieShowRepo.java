package com.example.cinemahub2.repository.user;

import com.example.cinemahub2.entity.Movie;
import com.example.cinemahub2.entity.MovieShow;
import com.example.cinemahub2.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieShowRepo extends JpaRepository<MovieShow, Integer> {
    List<MovieShow> findByMovie(Movie movie);
    List<MovieShow> findByTheater(Theater theater);
}