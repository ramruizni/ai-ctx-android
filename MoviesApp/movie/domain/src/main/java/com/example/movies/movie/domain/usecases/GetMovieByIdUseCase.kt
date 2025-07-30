package com.example.movies.movie.domain.usecases

import com.example.movies.movie.domain.MoviesRepository
import com.example.movies.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    
    operator fun invoke(id: Int): Flow<Movie?> {
        return repository.getMovieById(id)
    }
}