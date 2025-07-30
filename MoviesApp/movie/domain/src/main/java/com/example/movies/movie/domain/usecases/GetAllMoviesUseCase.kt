package com.example.movies.movie.domain.usecases

import com.example.movies.movie.domain.MoviesRepository
import com.example.movies.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class GetAllMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return moviesRepository.getAllMovies()
    }
}